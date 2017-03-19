package com.example.benearle.sebapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class StateActivity extends AppCompatActivity {

    private DatagramSocket socket;
    private InetAddress host;
    private int port;

    private void initConnection() throws Exception {
        host = InetAddress.getByName("10.0.0.13");
        port = 13375;
        socket = new DatagramSocket();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        byte[] data = "getState:".getBytes();
        try {
            initConnection();
            socket.send(new DatagramPacket(data, data.length, host, port));
        } catch (Exception e){
            System.out.println("ERROR");
        }
    }

}
