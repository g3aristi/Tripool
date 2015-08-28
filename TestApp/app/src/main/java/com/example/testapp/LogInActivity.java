package com.example.testapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class LogInActivity extends ActionBarActivity implements View.OnClickListener {

    Button bSignin;
    EditText etUsername, etPassword;
    TextView registerLink;
    UserLocalStorage userLocalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        bSignin = (Button) findViewById(R.id.bSignin);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        registerLink = (TextView) findViewById(R.id.registerLink);

        userLocalStorage = new UserLocalStorage(this);

        bSignin.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bSignin:

                User user = new User(null, null);
                userLocalStorage.storeUserData(user);

                userLocalStorage.setUserSignedIn(true);
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.registerLink:
                startActivity(new Intent(this, ActivitySignUp.class));
                break;
        }
    }
}
