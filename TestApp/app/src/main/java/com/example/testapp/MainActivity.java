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


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    ImageButton btnRanGen;
    EditText etName;
    Button bSignOut, bMyProfile, bMyPools, bHistory;
    UserLocalStorage userLocalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRanGen = (ImageButton) findViewById(R.id.imageButtonRanGen);
        etName = (EditText) findViewById(R.id.etName);
        bSignOut = (Button) findViewById(R.id.bSignOut);
        bMyProfile = (Button) findViewById(R.id.bMyProfile);
        bMyPools = (Button) findViewById(R.id.bMyPools);
        bHistory = (Button) findViewById(R.id.bHistory);

        bMyProfile.setOnClickListener(this);
        bMyPools.setOnClickListener(this);
        bHistory.setOnClickListener(this);
        bSignOut.setOnClickListener(this);

        userLocalStorage = new UserLocalStorage(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()){
            displayUserDetails();
        }

    }

    private boolean authenticate(){
        return userLocalStorage.getUserSignedIn();
    }

    private void displayUserDetails(){
        User u = userLocalStorage.getSignedInUser();

        etName.setText(u.name);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bMyProfile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.bMyPools:
                startActivity(new Intent(this, MyPoolsActivity.class));
                break;
            case R.id.bHistory:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            case R.id.bSignOut:
                userLocalStorage.clearUserData();
                userLocalStorage.setUserSignedIn(false);

                startActivity(new Intent(this, LogInActivity.class));
                break;
        }
    }
}
