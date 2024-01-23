package com.userLogin.model;

import java.util.List;

public class UserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String username;
    private String password;
    private List<Item> favoriteItems;

    private List<Order> tempOrder;


    public UserRequest() {
    }

    public UserRequest(Long id, String firstName, String lastName, String email, String phone, String address, String username,
                String password, List<Item> favoriteItems, List<Order> tempOrder) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.username = username;
        this.password = password;
        this.favoriteItems = favoriteItems;
        this.tempOrder = tempOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Item> getFavoriteItems() {
        return favoriteItems;
    }

    public void setFavoriteItems(List<Item> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    public List<Order> getTempOrder() {
        return tempOrder;
    }

    public void setTempOrder(List<Order> tempOrder) {
        this.tempOrder = tempOrder;
    }



    public User toCustomUser() {
        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                this.phone,
                this.address,
                this.username,
                this.password,
                this.favoriteItems,
                this.tempOrder
        );
    }
}

