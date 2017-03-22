package com.example.benearle.sebapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SettingsActivity extends AppCompatActivity {
    private final String serverIP = "192.168.145.130";
    private final int port =  13375;
    private TextView maxSpeed;
    private TextView radius;
    private DatagramSocket socket;
    private InetAddress host;

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

    //Network Initialization
    private void netInit(){
        try {
            socket = new DatagramSocket();
            host = InetAddress.getByName(serverIP);
        } catch (Exception e) {
            System.out.println("Error in netInit().");
            System.out.println(e.getStackTrace());
        }
    }

    private void send(byte[] data){
        try {
            socket.send(new DatagramPacket(data, data.length, host, port));
        } catch (Exception e ){
            System.out.println("Error in send().");
            System.out.println(e.getStackTrace());
        }
    }

}
