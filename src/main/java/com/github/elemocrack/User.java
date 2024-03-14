package com.github.elemocrack;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private String name;
    private String password;
    private double balance;
    private int userId;
    private static AtomicInteger lastId = new AtomicInteger(0);


    public User() {
        userId = lastId.getAndIncrement();
    }

    public User(String name, String password, double balance) {
        this.name = name;
        this.password = password;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
