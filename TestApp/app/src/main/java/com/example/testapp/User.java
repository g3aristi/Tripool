package com.example.testapp;

/**
 * Created by gilberto on 2015-08-27.
 */
public class User {

    String name, email, password;

    public User (String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public User (String email, String password){
        this.email = email;
        this.password = password;
        this.name = "";
    }
}
