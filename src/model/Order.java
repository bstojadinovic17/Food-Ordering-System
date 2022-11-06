package model;

import states.OrderState;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private int orderId;
    private Restaurant restaurant;
    private Customer customer;
    private ArrayList<String> orderItems;
    private int price;
    private String orderDetails;
    private boolean isPastOrder;
    private OrderState state;

    public int getOrderId() {
        return orderId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<String> getOrderItems() {
        return orderItems;
    }

    public int getPrice() {
        return price;
    }


    public String getOrderDetails() {
        return orderDetails;
    }

    public OrderState getState() {
        return state;
    }

    public boolean isPastOrder() {
        return isPastOrder;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderItems(ArrayList<String> orderItems) {
        this.orderItems = orderItems;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void setPastOrder(boolean pastOrder) {
        isPastOrder = pastOrder;
    }
}
