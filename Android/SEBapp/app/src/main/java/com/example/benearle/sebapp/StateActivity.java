package com.example.benearle.sebapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.*;
import android.view.*;
import android.widget.TextView;

public class StateActivity extends AppCompatActivity {
    private boolean debug = false;
    TextView textView;
    RequestStateInterface rs;

    private void updateInfo(){
        //Thread udpSendThread = new Thread(new Runnable() {
        runOnUiThread(new Runnable() {
        @Override
            public void run() {
                HashMap<String, String> state = rs.getState();
                for (String key : state.keySet()) {
                    if (key == "Speed")
                        textView.setText("Speed: " + Integer.parseInt(state.get(key)));
                }
                System.out.println(rs);
            }
        });
    }


    public void refresh (View view) {
        updateInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.speedView);

        //used for stubbing out the server communication since networking from the emulator is hard.
        if(debug) rs = new RequestStateStub();
        else rs = new RequestState();

        updateInfo();

    }

}