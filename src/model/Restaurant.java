package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant extends User implements Serializable {
    private String name;
    private String address;
    private ArrayList<String> menuItems;
    private ArrayList<Order> activeOrders;
    private String restaurantInfo;

    public Restaurant(int id) {
        super(id);
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getMenuItems() {
        return menuItems;
    }

    public ArrayList<Order> getActiveOrders() {
        return activeOrders;
    }

    public String getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMenuItems(ArrayList<String> menuItems) {
        this.menuItems = menuItems;
    }

    public void setActiveOrders(ArrayList<Order> activeOrders) {
        this.activeOrders = activeOrders;
    }

    public void setRestaurantInfo(String restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }



    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                '}';
    }
}
