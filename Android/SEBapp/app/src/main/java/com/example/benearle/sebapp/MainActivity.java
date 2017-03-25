package com.example.benearle.sebapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import java.net.*;

public class MainActivity extends AppCompatActivity {
    public final static String ip = "192.168.0.31";
    public final static int port = 13375;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showState(View view) {
        Intent intent = new Intent(this, StateActivity.class);
        startActivity(intent);
    }


    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startRide(View view) {
        Toast.makeText(this, "Starting a new ride...", Toast.LENGTH_SHORT).show();
        final byte[] data = {'n', 'e', 'w', 'R', 'i', 'd', 'e', ':', '1'};
        try {
            Thread myThread = new Thread(new UDPSend(data));
            myThread.start();
        } catch (Exception e ){
            System.out.println("Error in send().");
            System.out.println(e.getStackTrace());
        }
    }
}
