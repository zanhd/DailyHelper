package com.zanhd.dailyhelper.model;

public class Customer {
    private int id;
    private String phoneNumber;
    private String name;
    private String emailAddress;
    private String password;

    public Customer() {}

    public Customer(String phoneNumber, String name, String password) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
    }

    public Customer(String phoneNumber, String name, String emailAddress, String password) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public Customer(int id, String phoneNumber, String name, String emailAddress, String password) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
