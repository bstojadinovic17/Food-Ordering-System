package view;

import model.Order;
import model.Restaurant;
import utils.LoggedIn;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* This class represents a main window for a Restaurant user.
* */

public class RestaurantView extends JFrame{
    private static RestaurantView instance = null;
    private LoggedIn loggedIn;
    private JButton btnLog;
    private JPanel ordersPanel;
    private JPanel restaurantInfo;
    private Restaurant currentRestaurant;
    private Order currentOrder;

    private RestaurantView(){

    }
    private void initialize() {
        initializeView();
    }

    private void initializeView() {
        setTitle("Restaurant portal");
        setSize(900,400);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        ordersPanel = new JPanel();
        add(ordersPanel);

        btnLog = new JButton("Logout");
        btnLog.setBounds(750,20,100,30);
        btnLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                //LoginView.getInstance().setVisible(true);
                setCurrentOrder(null);
                WelcomeView view = new WelcomeView();
                if(view.getResult() == 0){
                    CustomerView.getInstance().getBtnLogin().setText("Login");
                    CustomerView.getInstance().getBtnOrders().setVisible(false);
                    CustomerView.getInstance().getBtnInfo().setVisible(false);
                    CustomerView.getInstance().getRecentlyOrdered().setVisible(false);
                    CustomerView.getInstance().setVisible(true);
                }else{
                    LoginView.getInstance().setVisible(true);
                }
            }
        });
        add(btnLog);
        restaurantInfo = new JPanel();
        add(restaurantInfo);
        setVisible(true);
    }

    public static RestaurantView getInstance(){
        if(instance == null){
            instance = new RestaurantView();
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

    public JButton getBtnLog() {
        return btnLog;
    }

    public Restaurant getCurrentRestaurant() {
        return currentRestaurant;
    }

    public void setCurrentRestaurant(Restaurant currentRestaurant) {
        this.currentRestaurant = currentRestaurant;
    }

    public JPanel getOrdersPanel() {
        return ordersPanel;
    }

    public JPanel getRestaurantInfo() {
        return restaurantInfo;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

}
