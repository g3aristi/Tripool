package com.example.testapp;

import android.app.AlertDialog;
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
    EditText etEmail, etPassword;
    TextView registerLink;
    UserLocalStorage userLocalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        bSignin = (Button) findViewById(R.id.bSignin);
        etEmail = (EditText) findViewById(R.id.etEmail);
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

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(email, password);
                userLocalStorage.storeUserData(user);

                authenticate(user);

                userLocalStorage.setUserSignedIn(true);
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.registerLink:
                startActivity(new Intent(this, ActivitySignUp.class));
                break;
        }
    }

    private void authenticate(User u){
        ServerRequest sr = new ServerRequest(this);
        sr.fetchUserDataBackground(u, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null){
                    showErrorMessage();
                }else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder db = new AlertDialog.Builder(LogInActivity.this);
        db.setMessage("Incorrect user details");
        db.setPositiveButton("OK", null);
        db.show();
    }

    private void logUserIn(User returnedUser){
        userLocalStorage.storeUserData(returnedUser);
        userLocalStorage.setUserSignedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }
}
