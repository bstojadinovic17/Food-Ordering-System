package db;

import model.Customer;
import model.Order;
import model.Restaurant;
import model.User;
import states.DeliveredState;
import states.OrderedState;
import states.ReceivedState;
import view.CustomerView;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
/*
* This class implements the Database class and stores the implementation of every method that has been
* declared in the Database class
* */
public class DBImpl implements Database{


    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:SQliteDB.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Implementation of adding a newly created order to a database
    @Override
    public void addOrder(Order order) {
        String query = "INSERT INTO orders(status,items,price,customer_id,restaurant_id) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, order.getState().toString());
            String allItems = "";
            for(String s: order.getOrderItems()){
                allItems = allItems + s + ";";
            }
            pstmt.setString(2, allItems);
            pstmt.setInt(3, order.getPrice());
            pstmt.setInt(4, order.getCustomer().getId());
            pstmt.setInt(5,order.getRestaurant().getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    // Implementation of an order's update to a database
    @Override
    public void updateOrder(Order order) {
        String query = "UPDATE orders SET status = ? " + "WHERE order_id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, order.getState().toString());
            pstmt.setInt(2, order.getOrderId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // This method loads all the data from a certain table in a database
    @Override
    public ArrayList<Object> loadData(String tableName) {
        String query = "";
        int len = 0;
        query = "SELECT * FROM " + tableName;
        ArrayList<Object> objects = new ArrayList<Object>();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){

            while (rs.next()) {
                if(!tableName.equals("orders")){
                    if(tableName.equals("customers")){
                        Customer customer = new Customer(rs.getInt("customer_id"));
                        customer.setName(rs.getString("name"));
                        customer.setAddress(rs.getString("address"));
                        customer.setUsername(rs.getString("username"));
                        customer.setPassword(rs.getString("password"));
                        customer.setPastOrders(loadOrders(tableName,customer.getId()));
                        objects.add(customer);
                    }else{
                        Restaurant restaurant = new Restaurant(rs.getInt("restaurant_id"));
                        restaurant.setName(rs.getString("name"));
                        restaurant.setAddress(rs.getString("address"));
                        restaurant.setUsername(rs.getString("username"));
                        restaurant.setPassword(rs.getString("password"));
                        ArrayList<String> items = new ArrayList<>();
                        items.add(rs.getString("menu_items"));
                        restaurant.setMenuItems(items);
                        restaurant.setActiveOrders(loadOrders(tableName,restaurant.getId()));
                        objects.add(restaurant);
                    }

                }else{
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    Restaurant restaurant_order = new Restaurant(rs.getInt("restaurant_id"));
                    order.setRestaurant(restaurant_order);
                    Customer customer_order = new Customer(rs.getInt("customer_id"));
                    order.setCustomer(customer_order);
                    ArrayList<String> items = new ArrayList<>();
                    items.add(rs.getString("items"));
                    order.setOrderItems(items);
                    order.setPrice(rs.getInt("price"));
                    objects.add(order);
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return objects;
    }
    // This method loads all the user's orders, depends on a userID
    @Override
    public ArrayList<Order> loadOrders(String tableName, int userID) {
        String query = "";
        ArrayList<Order> orders = new ArrayList<>();
        if(tableName.equals("customers")){
            query = "SELECT * FROM orders WHERE customer_id = "+userID;
            try(Connection conn = this.connect()){
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    Restaurant restaurant_order = new Restaurant(rs.getInt("restaurant_id"));
                    order.setRestaurant(restaurant_order);
                    Customer customer_order = new Customer(rs.getInt("customer_id"));
                    order.setCustomer(customer_order);
                    ArrayList<String> items = new ArrayList<>();
                    items.add(rs.getString("items"));
                    order.setOrderItems(items);
                    order.setPrice(rs.getInt("price"));
                    if(rs.getString("status").equals("Ordered")){
                        order.setState(new OrderedState());
                    }else if(rs.getString("status").equals("Received")){
                        order.setState(new ReceivedState());
                    }else{
                        order.setState(new DeliveredState());
                    }
                    orders.add(order);
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }else{
            query = "SELECT * FROM orders WHERE restaurant_id = "+userID+" AND status LIKE '%Ordered%' OR status LIKE '%Received%'";
            try(Connection conn = this.connect()){
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    Restaurant restaurant_order = new Restaurant(rs.getInt("restaurant_id"));
                    order.setRestaurant(restaurant_order);
                    Customer customer_order = new Customer(rs.getInt("customer_id"));
                    order.setCustomer(customer_order);
                    ArrayList<String> items = new ArrayList<>();
                    items.add(rs.getString("items"));
                    order.setOrderItems(items);
                    order.setPrice(rs.getInt("price"));
                    if(rs.getString("status").equals("Ordered")){
                        order.setState(new OrderedState());
                    }else if(rs.getString("status").equals("Received")){
                        order.setState(new ReceivedState());
                    }else{
                        order.setState(new DeliveredState());
                    }
                    orders.add(order);
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return orders;
    }
    //This method returns the current logged user, either returns a customer user or a restaurant user
    @Override
    public User getCurrentUser(String username, String password, String tableName) {
        if(tableName.equals("customers")){
            ArrayList<Object> objects = loadData(tableName);
            for(Object o:objects){
                Customer c = (Customer) o;
                if(c.getUsername().equals(username) && c.getPassword().equals(password)){
                    return c;
                }
            }

        }else{
            ArrayList<Object> objects = loadData(tableName);
            for(Object o:objects){
                Restaurant r = (Restaurant) o;
                if(r.getUsername().equals(username) && r.getPassword().equals(password)){
                    return r;
                }
            }
        }
        return null;
    }

    // This method updates logged in user's data in a database
    @Override
    public void changeUserData(Customer customer) {
        String query = "UPDATE customers SET name = ? , "
                + "address = ? , "
                +" username = ? , "
                +" password = ? "
                + "WHERE customer_id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getUsername());
            pstmt.setString(4, customer.getPassword());
            pstmt.setInt(5, customer.getId());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"User Info changed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
