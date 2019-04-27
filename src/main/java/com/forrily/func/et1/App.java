package com.forrily.func.et1;

public class App{

    public static void main(String[] args) {
        UserFactory<User> userUserFactory = User::new;

        System.err.println(userUserFactory.creat(1, "linym"));
    }
}
