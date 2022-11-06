package view;

import main.AppCore;
import model.Customer;
import model.Order;
import model.Restaurant;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import states.DeliveredState;
import states.OrderedState;
import states.ReceivedState;
import utils.LoggedIn;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
/*
* This class displays a login form view.
* It also changes user's main window, depending on a user's type.
* If a user logges in as a customer, CustomerView is being changed to apply to that certain user
* If a user logges in as a restauranr, RestaurantView is being changed to apply to that certain restaurant
*
*
* Every user's credentials can be found in a utils/Credentials.txt file for testing, as well as in db/DBInit class
* where the initial data is being added to the database
* */
public class LoginView extends JFrame implements ActionListener {


    private static LoginView instance = null;
    //private int isRestaurant = 0;
    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");
    private int isRestaurant;

    public LoginView() {
        setTitle("Login");
        setVisible(true);
        setBounds(10,10,370,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);


    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of LOGIN button
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = passwordField.getText();
            int check = 0;
            // If user picks a customer on the WelcomeView, validation is being done for all the customers
            // If the customers credentials are correct, CustomerView is being changed to apply to that certain customer
            if(isRestaurant == 0){
                ArrayList<Object> objects = AppCore.getInstance().getDb().loadData("customers");
                ArrayList<Customer> customers = new ArrayList<>();
                for(Object o:objects){
                    Customer c = (Customer) o;
                    customers.add(c);
                }
                for(Customer cust:customers){
                    if(cust.getUsername().equals(userText) && cust.getPassword().equals(pwdText)){
                        check = 1;
                        this.setVisible(false);
                        ///System.out.println("usao");
                        Customer current = (Customer) AppCore.getInstance().getDb().getCurrentUser(userText,pwdText,"customers");
                        CustomerView.getInstance().setCurrentCustomer(current);
                        CustomerView.getInstance().setLoggedIn(LoggedIn.YES);
                        CustomerView.getInstance().getBtnInfo().setVisible(true);
                        CustomerView.getInstance().getBtnOrders().setVisible(true);
                        CustomerView.getInstance().getBtnLogin().setText("Logout");
                        CustomerView.getInstance().getRecentlyOrdered().removeAll();
                        CustomerView.getInstance().getRecentlyOrdered().setBounds(400,280,350,70);
                        CustomerView.getInstance().getRecentlyOrdered().setBorder(border);
                        CustomerView.getInstance().getRecentlyOrdered().add(new JLabel("Recently ordered:"));

                        int numPastOrders = current.getPastOrders().size();
                        int cnt = 0;
                        if(numPastOrders > 0){
                            JPanel order = new JPanel(new GridLayout(1,3));
                            order.setBorder(border);
                            order.add(new JLabel("Order " + current.getPastOrders().get(numPastOrders-1).getOrderId()));
                            order.add(new JLabel("                "));
                            JButton btnDetails = new JButton("Order Details");
                            btnDetails.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    CustomerView.getInstance().setCurrentOrder(current.getPastOrders().get(numPastOrders-1));
                                    new OrderInfoView();
                                }
                            });
                            order.add(btnDetails);
                            CustomerView.getInstance().getRecentlyOrdered().add(order);
                        }
                        //System.out.println(CustomerView.getInstance().getCurrentCustomer());
                        CustomerView.getInstance().getRecentlyOrdered().setVisible(true);
                        CustomerView.getInstance().setVisible(true);
                    }

                }
            }else{
                // User chose restaurant in WelcomeView and same logic applies as for a customer, but with RestaurantView being changed
                ArrayList<Object> objects = AppCore.getInstance().getDb().loadData("restaurants");
                ArrayList<Restaurant> restaurants = new ArrayList<>();
                for(Object o:objects){
                    Restaurant r = (Restaurant) o;
                    restaurants.add(r);
                }
                for(Restaurant rest: restaurants){
                    if(rest.getUsername().equals(userText) && rest.getPassword().equals(pwdText)){
                        check = 1;
                        this.setVisible(false);
                        Restaurant current = (Restaurant) AppCore.getInstance().getDb().getCurrentUser(userText,pwdText,"restaurants");
                        RestaurantView.getInstance().getOrdersPanel().removeAll();
                        RestaurantView.getInstance().getOrdersPanel().setLayout(new GridLayout(4,1));
                        RestaurantView.getInstance().getOrdersPanel().setBounds(20,20,450, 300);
                        JLabel label = new JLabel("Active Orders:");
                        label.setBounds(5,5,50,10);
                        RestaurantView.getInstance().getOrdersPanel().add(label);
                        ArrayList<Order> activeOrders = current.getActiveOrders();

                        for(int i=0;i<activeOrders.size();i++){
                            JPanel order = new JPanel();
                            order.setLayout(new GridLayout(2,4));
                            JLabel orderName = new JLabel("Order name "+ activeOrders.get(i).getOrderId());
                            order.add(orderName);
                            order.add(new JLabel());
                            order.add(new JLabel());
                            order.add(new JLabel());
                            JButton btnDetails = new JButton("Order Details");
                            btnDetails.setSize(new Dimension(50,25));
                            int tmp = i;
                            btnDetails.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    RestaurantView.getInstance().setCurrentOrder(activeOrders.get(tmp));
                                    new OrderInfoView();
                                }
                            });
                            order.add(btnDetails);
                            JButton btnChange = new JButton("Change order");
                            btnChange.setSize(new Dimension(50,25));
                            btnChange.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    Order order = activeOrders.get(tmp);
                                    AppCore.getInstance().getDb().updateOrder(order);
                                    JOptionPane.showMessageDialog(null,"Order successfully updated!");
                                }
                            });
                            order.add(btnChange);
                            JButton btnUpdate = new JButton("Update status");
                            btnUpdate.setSize(new Dimension(50,25));
                            btnUpdate.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String[] options = {"Delivered","Received","Ordered"};
                                    Order order = activeOrders.get(tmp);
                                    JComboBox<String> comboBox = new JComboBox(options);
                                    comboBox.setBounds(10,10,200,50);
                                    JDialog dialog = new JDialog();
                                    dialog.setLocationRelativeTo(null);
                                    dialog.setSize(300,200);
                                    dialog.setLayout(null);
                                    dialog.add(comboBox);
                                    JButton bntOk = new JButton("Ok");
                                    bntOk.setBounds(100,100,50,25);
                                    bntOk.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if(comboBox.getSelectedItem().equals("Ordered")){
                                                order.setState(new OrderedState());
                                            }else if(comboBox.getSelectedItem().equals("Received")){
                                                order.setState(new ReceivedState());
                                            }else{
                                                order.setState(new DeliveredState());
                                            }
                                            dialog.setVisible(false);
                                        }
                                    });
                                    dialog.add(bntOk);
                                    dialog.setVisible(true);


                                }
                            });
                            order.add(btnUpdate);
                            JButton btnExport = new JButton("Export order");
                            btnExport.setSize(new Dimension(50,50));
                            btnExport.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JPanel panel = new JPanel();
                                    panel.add(new JLabel("Choose a format"));
                                    Object[] options = {"TXT", "EXCEL"};
                                    int result = JOptionPane.showOptionDialog(null, panel, "Choose a option",
                                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                                            null, options, null);
                                    if(result == 0){
                                        try {
                                            File file = new File("/exports/order"+activeOrders.get(tmp).getOrderId()+".txt");
                                            FileOutputStream fos = new FileOutputStream(file.getName());
                                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                                            oos.writeObject(activeOrders.get(tmp));
                                            JOptionPane.showMessageDialog(null,"Successfully exported an order!");
                                        } catch (FileNotFoundException fileNotFoundException) {
                                            fileNotFoundException.printStackTrace();
                                        } catch (IOException ioException) {
                                            ioException.printStackTrace();
                                        }
                                    }else{
                                            /*ArrayList<Order> orderList = new ArrayList<Order>();
                                            orderList.add(activeOrders.get(tmp));

                                            Workbook workbook = new HSSFWorkbook();
                                            Sheet sheet = workbook.createSheet("sheet1");
                                            int rownum = 0;
                                            Row row = sheet.createRow(rownum);
                                            Cell cell = row.createCell(0);
                                            cell.setCellValue(activeOrders.get(tmp).getOrderId());
                                            cell = row.createCell(1);
                                            cell.setCellValue(activeOrders.get(tmp).getCustomer().getName());
                                            cell = row.createCell(2);
                                            cell.setCellValue(activeOrders.get(tmp).getRestaurant().getName());
                                            cell = row.createCell(3);
                                            cell.setCellValue(activeOrders.get(tmp).getOrderItems().get(0));
                                            cell = row.createCell(4);
                                            cell.setCellValue(activeOrders.get(tmp).getPrice());

                                            try {
                                                FileOutputStream out = new FileOutputStream(new File("order"+activeOrders.get(tmp).getOrderId()+".xlsx"));
                                                workbook.write(out);
                                                JOptionPane.showMessageDialog(null,"Order exported successfully!");
                                                out.close();
                                            } catch (FileNotFoundException fileNotFoundException) {
                                                fileNotFoundException.printStackTrace();
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            }*/


                                    }

                                }
                            });
                            order.add(btnExport);

                            RestaurantView.getInstance().getOrdersPanel().add(order);
                        }
                        RestaurantView.getInstance().setCurrentRestaurant(current);
                        RestaurantView.getInstance().getOrdersPanel().setVisible(true);

                        RestaurantView.getInstance().getRestaurantInfo().removeAll();
                        RestaurantView.getInstance().getRestaurantInfo().setBorder(border);
                        RestaurantView.getInstance().getRestaurantInfo().setLayout(new GridLayout(6,1));
                        RestaurantView.getInstance().getRestaurantInfo().setBounds(500, 130, 350, 200);
                        JLabel label2 = new JLabel("Restaurant information:");
                        RestaurantView.getInstance().getRestaurantInfo().add(label2);
                        RestaurantView.getInstance().getRestaurantInfo().add(new JLabel());
                        JLabel lblName = new JLabel("Name: ");
                        RestaurantView.getInstance().getRestaurantInfo().add(lblName);
                        JLabel txtName = new JLabel(current.getName());
                        RestaurantView.getInstance().getRestaurantInfo().add(txtName);

                        JLabel lblAddress = new JLabel("Address: ");
                        RestaurantView.getInstance().getRestaurantInfo().add(lblAddress);
                        JLabel txtAddress= new JLabel(current.getAddress());
                        RestaurantView.getInstance().getRestaurantInfo().add(txtAddress);

                        JLabel lblUsername = new JLabel("Username: ");
                        RestaurantView.getInstance().getRestaurantInfo().add(lblUsername);
                        JLabel txtUsername= new JLabel(current.getUsername());
                        RestaurantView.getInstance().getRestaurantInfo().add(txtUsername);

                        JLabel lblPassword = new JLabel("Password: ");
                        RestaurantView.getInstance().getRestaurantInfo().add(lblPassword);
                        JLabel txtPassword= new JLabel(current.getPassword());
                        RestaurantView.getInstance().getRestaurantInfo().add(txtPassword);

                        JLabel lblItems = new JLabel("Menu Items: ");
                        RestaurantView.getInstance().getRestaurantInfo().add(lblItems);
                        JLabel txtItems= new JLabel(current.getMenuItems().get(0));
                        RestaurantView.getInstance().getRestaurantInfo().add(txtItems);
                        //System.out.println(RestaurantView.getInstance().getCurrentRestaurant().getName());
                        CustomerView.getInstance().setVisible(false);
                        RestaurantView.getInstance().setVisible(true);
                    }
                }
            }
            userTextField.setText("");
            passwordField.setText("");
            if(check == 0){
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }


        }
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }


        }
    }
    public static LoginView getInstance(){
        if(instance == null){
            instance = new LoginView();
        }
        return instance;
    }

    public int getIsRestaurant() {
        return isRestaurant;
    }

    public void setIsRestaurant(int isRestaurant) {
        this.isRestaurant = isRestaurant;
    }
}
