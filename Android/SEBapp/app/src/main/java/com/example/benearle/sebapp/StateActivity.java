package com.example.benearle.sebapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.*;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class StateActivity extends AppCompatActivity {
    private int speed;
    TextView textView;
    RequestState rs;

    private void updateInfo() throws Exception{
        HashMap<String, String> state = new HashMap<>();
        for (String key : state.keySet()){
            if(key == "Speed") speed = Integer.parseInt(state.get(key));
        }
        textView.setText("Speed: " + speed);
    }


    public void refresh (View view) {
        try{
            updateInfo();
        } catch (Exception e){
            System.out.println("Could not refresh: " + e.toString());
        }
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
