package com.example.benearle.sebapp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by BenEa on 3/24/2017.
 */

class RunnableGetState implements Runnable {
    //This is the data that will be sent to the server in order to get the state
    private final byte[] data = "getState:13378".getBytes();
    //Standard networking variables
    private final int PACKETSIZE = 500;
    private final String serverIP = MainActivity.ip;
    private int port = MainActivity.port;
    private HashMap<String, String> state;
    private DatagramSocket socket, s;
    private InetAddress host;
    String[] pairs;



    public RunnableGetState(HashMap<String, String> state) {
        this.state = state;
    }

    public void run() {
        try {
            socket = new DatagramSocket();
            host = InetAddress.getByName(serverIP);
            s = new DatagramSocket(13378);
            socket.send(new DatagramPacket(data, data.length, host, port));
            DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE );
            s.receive(packet);
            String str = new String(packet.getData());
            pairs = str.trim().split(" ");
            //must be even since we are getting variable:value pairs
            for(int i = 0; i < pairs.length; i++){
                String[] combo = pairs[i].split(":");
                assert(combo.length == 2);
                state.put(combo[0], combo[1]);
            }
            socket.close();
            s.close();
        } catch(Exception e) {
            System.out.println("Error getting state.");
            System.out.println(e.getStackTrace());
        }
    }
}