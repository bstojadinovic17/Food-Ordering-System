package view;

import model.Order;

import javax.swing.*;
/*
* This view opens a new window which shows details of a certain order which the user wants to see.
* */
public class OrderInfoView extends JFrame {

    public OrderInfoView(){

        setTitle("Order Details");
        setSize(400,400);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        Order order;
        //System.out.println(CustomerView.getInstance().getCurrentOrder());
        //System.out.println(RestaurantView.getInstance().getCurrentOrder());

        if(CustomerView.getInstance().getCurrentOrder() == null){
            order = RestaurantView.getInstance().getCurrentOrder();
        }else {
            order = CustomerView.getInstance().getCurrentOrder();
        }
        JLabel lblId = new JLabel("Order id: ");
        lblId.setBounds(10,20,100,20);
        add(lblId);
        JLabel txtId = new JLabel(order.getOrderId()+"");
        txtId.setBounds(120,20,200,20);
        add(txtId);

        JLabel lblStatus = new JLabel("Order status: ");
        lblStatus.setBounds(10,50,100,20);
        add(lblStatus);
        JLabel txtStatus= new JLabel(order.getState().toString());
        txtStatus.setBounds(120,50,200,20);
        add(txtStatus);

        JLabel lblItems = new JLabel("Order items: ");
        lblItems.setBounds(10,80,100,20);
        add(lblItems);
        JLabel txtItems= new JLabel(order.getOrderItems().get(0));
        txtItems.setBounds(120,80,200,20);
        add(txtItems);

        JLabel lblPrice = new JLabel("Order price: ");
        lblPrice.setBounds(10,110,100,20);
        add(lblPrice);
        JLabel txtPrice= new JLabel(order.getPrice()+"");
        txtPrice.setBounds(120,110,200,20);
        add(txtPrice);

        JLabel lblFor = new JLabel("Order for: ");
        lblFor.setBounds(10,140,100,20);
        add(lblFor);
        JLabel txtFor= new JLabel(order.getCustomer().getId()+"");
        txtFor.setBounds(120,140,200,20);
        add(txtFor);

        JLabel lblFrom = new JLabel("Order from: ");
        lblFrom.setBounds(10,170,100,20);
        add(lblFrom);
        JLabel txtFrom= new JLabel(order.getRestaurant().getId()+"");
        txtFrom.setBounds(120,110,200,20);
        add(txtFrom);

        setVisible(true);
    }
}
