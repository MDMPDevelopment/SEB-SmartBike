package com.example.benearle.sebapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.util.*;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import org.w3c.dom.Text;

import java.util.concurrent.*;
//import java.util.concurrent.*;

public class StateActivity extends AppCompatActivity {
    private boolean debug = true;
    TextView speed;
    TextView textView3;
    //HashMap<String, String> state;
    //RequestStateInterface rs;

    private void updateInfo(){
        Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
        new AsyncGetState(this).execute();
    }

    public void setState(HashMap<String, String> state){
        String s = "";
        for (String key : state.keySet()) {
            if (key.equals("Speed"))
                speed.setText("Speed: " + state.get(key));
            if (key.equals("turnL") && state.get(key).equals("1"))
                s+= "Turning left!\n";
            if (key.equals("turnR") && state.get(key).equals("1"))
                s+= "Turning Right!\n";
            if (key.equals("brake") && state.get(key).equals("1"))
                s+= "Braking!\n";
        }
        textView3.setText(s);
    }

    public void refresh (View view) {
        //updateInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        speed = (TextView) findViewById(R.id.speedView);
        textView3 = (TextView) findViewById(R.id.textView3);
        //used for stubbing out the server communication since networking from the emulator is hard.
//        if(debug) rs = new RequestStateStub();
//        else rs = new RequestState();

        updateInfo();

    }

    private class AsyncGetState extends AsyncTask<Void, Void, Void> {

        HashMap<String, String> state;
        StateActivity sa;

        AsyncGetState(StateActivity sa){
            this.sa = sa;
        }

        protected Void doInBackground(Void... params) {
//            rs = new RequestState();
            //ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            //Runnable toRun = new Runnable() {
               // public void run() {
                    sa.setState(new RequestState().getState());
              //  }
            //};

            //ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate(toRun, 1, 1, TimeUnit.SECONDS);
            return null;
        }
    }

}