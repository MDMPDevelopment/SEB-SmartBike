package com.example.benearle.sebapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.*;
import android.view.*;
import android.widget.TextView;

public class StateActivity extends AppCompatActivity {
    private boolean debug = true;
    TextView textView;
    RequestStateInterface rs;

    private void updateInfo(){
        HashMap<String, String> state = rs.getState();
        for (String key : state.keySet()){
            if(key == "Speed") textView.setText("Speed: " + Integer.parseInt(state.get(key)));
        }

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