package db;

import model.Customer;
import model.Order;
import model.User;
/*
* Database interface which stores declarations of all methods which are used
* to communicate with a SQlite database
* */
import java.util.ArrayList;

public interface Database {

    void addOrder(Order order);

    void updateOrder(Order orded);

    ArrayList<Object> loadData(String tableName);

    ArrayList<Order> loadOrders(String tableName, int userID);

    User getCurrentUser(String username, String password, String tableName);

    void changeUserData(Customer customer);
}
