package view;

import main.AppCore;
import model.Order;
import model.Restaurant;
import states.OrderedState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/*
* This view is created when the user clicks on a Order button
* It displays a form to create a new order
* If the order is created correctly, after submitting, new order is added to a database
* */
public class OrderView extends JFrame {

    private int full_total = 0;
    private Order newOrder;
    private int isCalculated = 0;

    public OrderView(){
        setTitle("Order form");
        setSize(800,800);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        Restaurant restaurant = CustomerView.getInstance().getCurrentRestaurant();
        ArrayList<String> itemsForOrder = new ArrayList<>();


        JLabel label = new JLabel("Order From " + restaurant.getName());
        label.setBounds(350,10,200,50);
        add(label);

        JLabel lblItem = new JLabel("Menu Item:");
        lblItem.setBounds(20,150,150,30);
        add(lblItem);
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(180,150,150,30);
        add(lblQuantity);
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(340,150,150,30);
        add(lblPrice);
        JLabel lblTotals = new JLabel("Totals:");
        lblTotals.setBounds(500,150,150,30);
        add(lblTotals);

        int cntX = 20;
        int cntY = 200;
        String[] items = restaurant.getMenuItems().get(0).split(";");
        for(int i=0;i< items.length;i++){
            JLabel itemName = new JLabel(items[i]);
            itemName.setBounds(cntX,cntY,150,30);
            itemName.setName(items[i]);
            add(itemName);
            cntX = cntX + 160;
            JTextField tQuantity = new JTextField();
            tQuantity.setBounds(cntX,cntY,150,30);
            tQuantity.setName("q"+items[i]);
            tQuantity.setText("0");
            add(tQuantity);
            cntX = cntX + 160;
            JLabel price = new JLabel(String.valueOf(i+5));
            price.setBounds(cntX,cntY,150,30);
            price.setName(String.valueOf(i+5));
            add(price);
            cntX = cntX+ 160;
            JTextField tTotals = new JTextField();
            tTotals.setBounds(cntX,cntY,150,30);
            tTotals.setEnabled(false);
            tTotals.setName("totals "+items[i]);
            add(tTotals);
            cntX = 20;
            cntY = cntY + 40;
        }
        cntY = cntY + 20;
        JLabel subTotal = new JLabel("Total:");
        subTotal.setBounds(500, cntY,50,30);
        add(subTotal);
        JTextField tfTotal = new JTextField();
        tfTotal.setName("fullTotal");
        tfTotal.setBounds(570,cntY,80,30);
        tfTotal.setEnabled(false);
        add(tfTotal);

        JButton btnSubmit = new JButton("Submit order");
        cntY = cntY+50;
        btnSubmit.setBounds(530,cntY, 120,30);
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isCalculated == 0){
                    JOptionPane.showMessageDialog(null, "You have to calculate total first!");
                }else{
                    setVisible(false);
                    newOrder = new Order();
                    newOrder.setRestaurant(restaurant);
                    newOrder.setCustomer(CustomerView.getInstance().getCurrentCustomer());
                    newOrder.setState(new OrderedState());
                    newOrder.setPrice(full_total);
                    ArrayList<String> allItems = new ArrayList<>();
                    for(String s: itemsForOrder){
                        allItems.add(s);
                    }
                    newOrder.setOrderItems(allItems);
                    AppCore.getInstance().getDb().addOrder(newOrder);
                    JOptionPane.showMessageDialog(CustomerView.getInstance(),"Your order has been confirmed.");
                }

            }
        });
        add(btnSubmit);
        JButton btnCalculate = new JButton("Calculate price");
        btnCalculate.setBounds(400,cntY,120,30);
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cnt_quantity = 6;
                int cnt_total = 8;
                int quantity = 0;
                int hasQuantity = 0;
                int []totals = new int[items.length];



                for(int i=0;i<items.length;i++){
                    JTextField tf_q = (JTextField) getContentPane().getComponent(cnt_quantity);
                    JTextField tf_t = (JTextField) getContentPane().getComponent(cnt_total);
                    if(Integer.valueOf(tf_q.getText()) > 0){
                        hasQuantity = 1;
                        quantity = Integer.valueOf(tf_q.getText());
                        for(int k=0;k<quantity;k++){
                            itemsForOrder.add(getContentPane().getComponent(cnt_quantity-1).getName());
                        }
                        int price = Integer.valueOf(getContentPane().getComponent(cnt_quantity+1).getName());
                        totals[i] = price * quantity;
                        tf_t.setText(String.valueOf(totals[i]));
                    }
                    cnt_quantity = cnt_quantity + 4;
                    cnt_total = cnt_total + 4;
                }
                if(hasQuantity == 0){
                    JOptionPane.showMessageDialog(null, "You have to add at least 1 item!");
                }else{
                    isCalculated = 1;
                    for(int j=0;j<totals.length;j++){
                        full_total = full_total + totals[j];
                    }
                    if(itemsForOrder.size() > 25){
                        JOptionPane.showMessageDialog(null,"Cannot add more than 25 items!");
                        full_total = 0;
                    }
                    JTextField tf_full = (JTextField) getContentPane().getComponent(cnt_total-2);
                    tf_full.setText(String.valueOf(full_total));
                }


            }
        });
        add(btnCalculate);
        setVisible(true);
    }

    public Order getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(Order newOrder) {
        this.newOrder = newOrder;
    }

}
