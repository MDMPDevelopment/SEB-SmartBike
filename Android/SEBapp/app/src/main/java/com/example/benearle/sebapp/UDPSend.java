package com.example.benearle.sebapp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by BenEa on 3/24/2017.
 */

public class UDPSend implements Runnable{
    private DatagramSocket socket;
    private InetAddress host;
    private int port;
    private byte[] data;

    UDPSend(byte[] data){
        this.data = data;
    }

    public void run() {
        try {
            host = InetAddress.getByName(MainActivity.ip);
            port = 13375;
            socket = new DatagramSocket();
            DatagramPacket p = new DatagramPacket(data, data.length, host, port);
            //if (!socket.getBroadcast()) socket.setBraodcast(true);
            socket.send(p);
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        socket.close();
    }
}
