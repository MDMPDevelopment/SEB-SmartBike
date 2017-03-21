package com.example.benearle.sebapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.*;
import android.view.*;
import android.widget.TextView;

public class StateActivity extends AppCompatActivity {
    private int speed;
    TextView textView;
    RequestState rs;

    private void updateInfo(){
        HashMap<String, String> state = rs.getState();
        for (String key : state.keySet()){
            if(key == "Speed") speed = Integer.parseInt(state.get(key));
        }
        textView.setText("Speed: " + speed);
    }


    public void refresh (View view) {
        updateInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speed = 0;
        setContentView(R.layout.activity_state);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.speedView);
        rs = new RequestState();
        updateInfo();

    }

}