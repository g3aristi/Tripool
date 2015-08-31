package com.example.testapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gilberto on 2015-08-27.
 */
public class UserLocalStorage {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStorage(Context c){
        userLocalDatabase = c.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User u){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", u.name);
        spEditor.putString("email", u.email);
        spEditor.putString("password", u.password);
        spEditor.commit();
    }

    public User getSignedInUser(){
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");

        User storedUser = new User (name, email, password);
        return storedUser;
    }

    public void setUserSignedIn(boolean signedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("signedIn", signedIn);
        spEditor.commit();
    }

    public boolean getUserSignedIn(){
        return (userLocalDatabase.getBoolean("signedIn", false) == true);
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
