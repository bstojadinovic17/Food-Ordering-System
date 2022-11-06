package model;

import utils.UserType;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer extends User implements Serializable {

    private String name;
    private String address;
    private boolean orderInProgress;
    private int numOfOrders;
    private ArrayList<Order> pastOrders;


    public Customer(int id) {
        super(id);
        this.setType(UserType.CUSTOMER);
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isOrderInProgress() {
        return orderInProgress;
    }

    public int getNumOfOrders() {
        return numOfOrders;
    }

    public ArrayList<Order> getPastOrders() {
        return pastOrders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrderInProgress(boolean orderInProgress) {
        this.orderInProgress = orderInProgress;
    }

    public void setNumOfOrders(int numOfOrders) {
        this.numOfOrders = numOfOrders;
    }

    public void setPastOrders(ArrayList<Order> pastOrders) {
        this.pastOrders = pastOrders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                '}';
    }
}
