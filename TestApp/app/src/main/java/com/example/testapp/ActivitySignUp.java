package com.example.testapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ActivitySignUp extends ActionBarActivity implements View.OnClickListener {

    Button bSignUp;
    EditText etName, etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_sign_up);

        bSignUp = (Button) findViewById(R.id.bSignUp);
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bSignUp:

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                User SignedUpData = new User(name, email, password);

                startActivity(new Intent(this, LogInActivity.class));
                break;
        }
    }
}
