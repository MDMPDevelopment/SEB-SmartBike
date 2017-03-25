package com.example.benearle.sebapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SettingsActivity extends AppCompatActivity {
    private TextView maxSpeed;
    private TextView radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        maxSpeed = (TextView) findViewById(R.id.maxSpeed);
        radius = (TextView) findViewById(R.id.radius);
    }

    public void sendMaxSpeed(View view){
        send(("setMaxSpeed:" + maxSpeed.getText().toString()).getBytes());
    }

    public void sendRadius (View view){
        send(("setRadius:" + radius.getText().toString()).getBytes());
    }


    private void send(byte[] data){
        try {
            Thread myThread = new Thread(new UDPSend(data));
            myThread.start();
        } catch (Exception e ){
            System.out.println("Error in send().");
            System.out.println(e.getStackTrace());
        }
    }
}
