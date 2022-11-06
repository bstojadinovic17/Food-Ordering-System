package db;

import java.sql.*;
/*
* This class stores methods about initializing a SQlite database and filling it with initial data.
* Object of this class is only being instaciated once in the AppCore class, and then its being commented out
* so that the application could work without initializing database on every start.
* */
public class DBInit {

    private static Connection conn;

    public DBInit(){
        try {
            initializeDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void connect() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:SQliteDB.db");
    }

    private void closeConnection() throws SQLException {
        conn.close();
    }
    //Creates tables for a database
    private void createTables() throws SQLException, ClassNotFoundException {
        connect();
        Statement st = conn.createStatement();
        String query_customers = "CREATE TABLE IF NOT EXISTS customers (\n"
                + "	customer_id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	address text NOT NULL,\n"
                + "	username text NOT NULL,\n"
                + "	password text NOT NULL\n"
                + ");";
        st.execute(query_customers);

        String query_restaurants = "CREATE TABLE IF NOT EXISTS restaurants (\n"
                + "	restaurant_id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	address text NOT NULL,\n"
                + "	username text NOT NULL,\n"
                + "	password text NOT NULL,\n"
                + "	menu_items text NOT NULL\n"
                + ");";
        st.execute(query_restaurants);

        String query_orders = "CREATE TABLE IF NOT EXISTS orders (\n"
                + "	order_id integer PRIMARY KEY,\n"
                + "	status text NOT NULL,\n"
                + "	items text NOT NULL,\n"
                + "	price integer NOT NULL,\n"
                + "	customer_id integer NOT NULL,\n"
                + "	restaurant_id integer NOT NULL\n"
                + ");";
        st.execute(query_orders);
        closeConnection();
    }
    // Adds initial data to tables in a database
    private void addInitData() throws SQLException, ClassNotFoundException {
        connect();

        String insert_customers = "INSERT INTO customers(name,address,username,password) VALUES(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(insert_customers);
        ps.setString(1, "John Doe");
        ps.setString(2, "Address 1");
        ps.setString(3, "username");
        ps.setString(4, "1234");
        ps.executeUpdate();

        ps.setString(1, "Pera Peric");
        ps.setString(2, "Address 2");
        ps.setString(3, "pera123");
        ps.setString(4, "pera123");
        ps.executeUpdate();

        ps.setString(1, "Mika Mikic");
        ps.setString(2, "Address 3");
        ps.setString(3, "mika99");
        ps.setString(4, "0000");
        ps.executeUpdate();

        ps.setString(1, "Admin");
        ps.setString(2, "Address Admin");
        ps.setString(3, "admin");
        ps.setString(4, "admin");
        ps.executeUpdate();

        String insert_restaurants = "INSERT INTO restaurants(name,address,username,password,menu_items) VALUES(?,?,?,?,?)";
        ps = conn.prepareStatement(insert_restaurants);
        ps.setString(1, "Restaurant 1");
        ps.setString(2, "Address Rest 1");
        ps.setString(3, "rest1");
        ps.setString(4, "rest1");
        ps.setString(5, "item1;item2;item3;item4;item5;item6");
        ps.executeUpdate();

        ps.setString(1, "Restaurant 2");
        ps.setString(2, "Address Rest 2");
        ps.setString(3, "rest2");
        ps.setString(4, "rest2");
        ps.setString(5, "item10;item15;item13;item18;item22;item28;item21;");
        ps.executeUpdate();

        ps.setString(1, "Restaurant 3");
        ps.setString(2, "Address Rest 3");
        ps.setString(3, "rest3");
        ps.setString(4, "rest3");
        ps.setString(5, "item1;item8;item15;item11;item19;item23;item29;item30");
        ps.executeUpdate();

        String insert_orders = "INSERT INTO orders(status,items,price,customer_id,restaurant_id) VALUES(?,?,?,?,?)";
        ps = conn.prepareStatement(insert_orders);
        ps.setString(1, "Delivered");
        ps.setString(2, "item22;item28;item21'item10");
        ps.setInt(3, 40);
        ps.setInt(4, 3);
        ps.setInt(5, 2);
        ps.executeUpdate();

        ps.setString(1, "Ordered");
        ps.setString(2, "item1;item2;item3'item4");
        ps.setInt(3, 30);
        ps.setInt(4, 2);
        ps.setInt(5, 1);
        ps.executeUpdate();

        ps.setString(1, "Delivered");
        ps.setString(2, "item1;item15;ite23");
        ps.setInt(3, 30);
        ps.setInt(4, 3);
        ps.setInt(5, 3);
        ps.executeUpdate();

        ps.setString(1, "Delivered");
        ps.setString(2, "item18;item22");
        ps.setInt(3, 20);
        ps.setInt(4, 1);
        ps.setInt(5, 2);
        ps.executeUpdate();

        ps.setString(1, "Received");
        ps.setString(2, "item4;item5;item6'item1");
        ps.setInt(3, 40);
        ps.setInt(4, 1);
        ps.setInt(5, 1);
        ps.executeUpdate();

        ps.setString(1, "Delivered");
        ps.setString(2, "item13");
        ps.setInt(3, 10);
        ps.setInt(4, 3);
        ps.setInt(5, 2);
        ps.executeUpdate();

        closeConnection();
    }
    //creates new database with tables and initial data
    public void initializeDB() throws SQLException, ClassNotFoundException {
        connect();
        createTables();
        addInitData();
    }
}
