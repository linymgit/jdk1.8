package com.forrily.func.et1;

public class User {
    int userId;
    String name;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
