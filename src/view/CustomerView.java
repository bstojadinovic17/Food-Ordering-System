package view;

import main.AppCore;
import model.Customer;
import model.Order;
import model.Restaurant;
import model.User;
import utils.LoggedIn;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/*
* This class displays main window for the customer user.
* */
public class CustomerView extends JFrame {
    private static CustomerView instance = null;
    private LoggedIn loggedIn = LoggedIn.NO;
    private JButton btnInfo;
    private JButton btnOrders;
    private JButton btnLogin;
    private JPanel recentlyOrdered;

    private Customer currentCustomer;
    private Order currentOrder;
    private Restaurant currentRestaurant = null;
    private Order newOrder = null;

    private CustomerView(){

    }
    private void initialize() {
        initializeView();
    }

    private void initializeView() {
        setTitle("Customer Portal");
        setSize(800,400);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JPanel restaurantsPanel = new JPanel();
        ArrayList<Object> restaurants = AppCore.getInstance().getDb().loadData("restaurants");
        restaurantsPanel.setBounds(20, 20, 350, 320);
        restaurantsPanel.setBorder(border);
        restaurantsPanel.setLayout(new GridLayout(restaurants.size()+1,1));
        JLabel label1 = new JLabel("Available Restaurants:");
        label1.setBounds(5,5,50,10);
        restaurantsPanel.add(label1);
        for(int i=0; i<restaurants.size();i++){
            Restaurant res = (Restaurant) restaurants.get(i);
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2,2));
            JLabel restaurantName = new JLabel(res.getName());
            panel.add(restaurantName);
            //panel.add(new JLabel());
            //panel.add(new JLabel());
            panel.add(new JLabel());
            String allitems = res.getMenuItems().get(0);
            String[] itemsTrimed = allitems.split(";");
            String menu = "Menu items:";
            for(String s:itemsTrimed){
                menu = menu + " " + s;
            }
            JLabel restautantMenu = new JLabel(menu);
            panel.add(restautantMenu);
            //panel.add(new JLabel());
            //panel.add(new JLabel());
            JButton btnOrder = new JButton("Order");
            btnOrder.setSize(new Dimension(50,25));
            btnOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(loggedIn == LoggedIn.YES){
                        setCurrentRestaurant(res);
                        OrderView orderView = new OrderView();
                    }else{
                        JOptionPane.showMessageDialog(CustomerView.getInstance(),"You have to login first!");
                    }
                }
            });
            panel.add(btnOrder);

            panel.setBorder(border);
            restaurantsPanel.add(panel);
        }
            add(restaurantsPanel);

        JPanel customerInfo = new JPanel();
        customerInfo.setLayout(new FlowLayout());
        customerInfo.setBounds(400,20,350,70);
        //customerInfo.setBorder(border);
            btnInfo = new JButton("User Info");
            btnInfo.setVisible(false);
            btnInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(loggedIn == LoggedIn.YES){
                        UserInfoView userInfo = new UserInfoView();
                    }else{
                        JOptionPane.showMessageDialog(CustomerView.getInstance(),"You have to login first!");
                    }
                }
            });
            customerInfo.add(btnInfo);
            btnOrders = new JButton("Past Orders");
            btnOrders.setVisible(false);
            btnOrders.addActionListener(AppCore.getInstance().getActionManager().getViewOrders());
            customerInfo.add(btnOrders);
            customerInfo.add(new JLabel("               "));
            btnLogin = new JButton("Log In");
            //btnLogout.setBounds(530, 25, 50, 25);
            btnLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(loggedIn == LoggedIn.YES){
                        setLoggedIn(LoggedIn.NO);
                        setVisible(false);
                        setCurrentOrder(null);
                        setCurrentRestaurant(null);
                        WelcomeView view = new WelcomeView();
                        if(view.getResult() == 0){
                            btnLogin.setText("Login");
                            btnOrders.setVisible(false);
                            btnInfo.setVisible(false);
                            recentlyOrdered.setVisible(false);
                            setVisible(true);
                        }else{
                            LoginView.getInstance().setVisible(true);
                            LoginView.getInstance().setIsRestaurant(1);
                            //RestaurantView.getInstance().setVisible(true);
                        }
                    }else{
                        setVisible(false);
                        LoginView.getInstance().setVisible(true);
                        LoginView.getInstance().setIsRestaurant(0);
                        //MainView.getInstance().setIsRestaurant(0);
                    }



                }
            });
            customerInfo.add(btnLogin);
        add(customerInfo);

        recentlyOrdered = new JPanel();

        //recentlyOrdered.setVisible(false);
        add(recentlyOrdered);
        setVisible(true);
    }

    public static CustomerView getInstance(){
        if(instance == null){
            instance = new CustomerView();
            instance.initialize();
        }

        return instance;
    }

    public LoggedIn getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(LoggedIn loggedIn) {
        this.loggedIn = loggedIn;
    }

    public JButton getBtnInfo() {
        return btnInfo;
    }

    public JButton getBtnOrders() {
        return btnOrders;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JPanel getRecentlyOrdered() {
        return recentlyOrdered;
    }

    public void setRecentlyOrdered(JPanel recentlyOrdered) {
        this.recentlyOrdered = recentlyOrdered;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Restaurant getCurrentRestaurant() {
        return currentRestaurant;
    }

    public void setCurrentRestaurant(Restaurant currentRestaurant) {
        this.currentRestaurant = currentRestaurant;
    }

    public Order getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(Order newOrder) {
        this.newOrder = newOrder;
    }

}
