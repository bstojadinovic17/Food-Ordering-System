package model;

import utils.UserType;

public class User {

    private int id;
    private String username;
    private String password;
    private UserType type;

    public User(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public UserType getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
