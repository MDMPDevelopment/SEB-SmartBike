package com.example.benearle.sebapp;
import java.util.HashMap;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class RequestState implements RequestStateInterface{
	public HashMap<String, String> getState(){
		try {
			HashMap<String, String> state = new HashMap<>();
			Thread myThread = new Thread(new RunnableGetState(state));
			myThread.start();
			myThread.join();
			return state;
		} catch (Exception e){
			System.out.println(e.getStackTrace());
		}
	return null;
	}
	public static void main(String [] args){
		RequestState rs = new RequestState();
		System.out.println(rs.getState().toString());
	}
}