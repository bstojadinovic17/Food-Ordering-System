package view;

import main.AppCore;
import model.Customer;
import model.Restaurant;
import model.User;
import utils.UserType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/*
* This view is opened when a user clicks on a User Info button
* It shows current user his general information, as well as the option
* to change his data
* */

public class UserInfoView extends JFrame {

    public UserInfoView(){
        setSize(300,300);
        setTitle("User Info");
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblName = new JLabel("Name: ");
        lblName.setBounds(10,20,100,20);
        add(lblName);
        JLabel txtName = new JLabel(CustomerView.getInstance().getCurrentCustomer().getName());
        txtName.setBounds(120,20,200,20);
        add(txtName);

        JLabel lblAddress = new JLabel("Address: ");
        lblAddress.setBounds(10,50,100,20);
        add(lblAddress);
        JLabel txtAddress= new JLabel(CustomerView.getInstance().getCurrentCustomer().getAddress());
        txtAddress.setBounds(120,50,200,20);
        add(txtAddress);

        JLabel lblUsername = new JLabel("Username: ");
        lblUsername.setBounds(10,80,100,20);
        add(lblUsername);
        JLabel txtUsername= new JLabel(CustomerView.getInstance().getCurrentCustomer().getUsername());
        txtUsername.setBounds(120,80,200,20);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setBounds(10,110,100,20);
        add(lblPassword);
        JLabel txtPassword= new JLabel(CustomerView.getInstance().getCurrentCustomer().getPassword());
        txtPassword.setBounds(120,110,200,20);
        add(txtPassword);

        JButton btnChange = new JButton("Change");
        btnChange.setBounds(150,140,100,20);
        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Name:", CustomerView.getInstance().getCurrentCustomer().getName());
                boolean changed = false;
                if(name!=null && !name.equals(CustomerView.getInstance().getCurrentCustomer().getName())){
                    changed = true;
                    CustomerView.getInstance().getCurrentCustomer().setName(name);
                }
                String address = JOptionPane.showInputDialog("Address:", CustomerView.getInstance().getCurrentCustomer().getAddress());
                if(address!=null && !address.equals(CustomerView.getInstance().getCurrentCustomer().getAddress())){
                    changed = true;
                    CustomerView.getInstance().getCurrentCustomer().setAddress(address);
                }
                String username = JOptionPane.showInputDialog("Username:", CustomerView.getInstance().getCurrentCustomer().getUsername());
                if(username!=null && !username.equals(CustomerView.getInstance().getCurrentCustomer().getUsername())){
                    changed = true;
                    CustomerView.getInstance().getCurrentCustomer().setUsername(username);
                }
                String password = JOptionPane.showInputDialog("Password:", CustomerView.getInstance().getCurrentCustomer().getPassword());
                if(password!=null && !password.equals(CustomerView.getInstance().getCurrentCustomer().getPassword())){
                    changed = true;
                    CustomerView.getInstance().getCurrentCustomer().setPassword(password);
                }
                if(changed){
                    AppCore.getInstance().getDb().changeUserData(CustomerView.getInstance().getCurrentCustomer());
                }else{
                    JOptionPane.showMessageDialog(null,"Nothing to be changed!");
                }
            }
        });
        add(btnChange);

        setVisible(true);

    }
}
