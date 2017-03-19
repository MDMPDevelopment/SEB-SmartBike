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
    private final int PACKETSIZE = 500;
    private final String ip = "192.168.145.130";
    private DatagramSocket socket;
    private InetAddress host;
    private int port;
    private int speed;
    TextView textView;

    private void initConnection() throws Exception {
        host = InetAddress.getByName(ip);
        port = 13375;
        socket = new DatagramSocket();
    }

    private void updateInfo() throws Exception{
        byte[] data = "getState:13375".getBytes();
        socket.send(new DatagramPacket(data, data.length, host, port));

        DatagramSocket s = new DatagramSocket(13375);
        DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE );
        s.receive(packet);

        String state = new String(packet.getData()).trim();
        String[] pairs = state.split(": ");
        for(int i = 0; i < pairs.length; i+=2){
            if(pairs[i] == "Speed") speed = Integer.parseInt(pairs[i+1]);
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
        try {
            initConnection();
        } catch (Exception e){
            System.out.println("ERROR in initConnection: " + e.getMessage());
        }

        try {
            updateInfo();
        } catch (Exception e){
            System.out.println("ERROR in updateInfo: " + e.getMessage());
        }

    }

}
