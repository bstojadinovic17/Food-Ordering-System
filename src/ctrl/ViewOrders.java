package ctrl;

import main.AppCore;
import model.Order;
import view.CustomerView;
import view.OrderInfoView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/*
* This action is triggered when a user wants to looks at his past orders
* It creates a view that contains a list with all users past orders
* */
public class ViewOrders extends AbstractAction {

    public ViewOrders(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame orders = new JFrame();
        orders.setSize(300,300);
        orders.setLocationRelativeTo(null);
        orders.setResizable(false);
        orders.setTitle("Past Orders");

        ArrayList<Order> pastOrders = CustomerView.getInstance().getCurrentCustomer().getPastOrders();
        JPanel panel = new JPanel();
        for(int i=0;i< pastOrders.size();i++){
            JLabel lblOrder = new JLabel("Order "+ pastOrders.get(i).getOrderId());
            panel.add(lblOrder);
            panel.add(new JLabel("                          "));
            JButton btnDetails = new JButton("View Details");
            int tmp = i;
            btnDetails.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CustomerView.getInstance().setCurrentOrder(pastOrders.get(tmp));
                    new OrderInfoView();
                }
            });
            btnDetails.setSize(100,30);
            panel.add(btnDetails);
        }
        orders.add(panel);
        orders.setVisible(true);
    }
}
