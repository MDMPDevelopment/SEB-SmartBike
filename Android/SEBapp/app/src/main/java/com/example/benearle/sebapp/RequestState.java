package com.example.benearle.sebapp;
import java.util.HashMap;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class RequestState implements RequestStateInterface{
    //This is the data that will be sent to the server in order to get the state
    private final byte[] data = "getState:13378".getBytes();
	//Standard networking variables
	private final int PACKETSIZE = 500;
	private String serverIP;
	private int port;
	private DatagramSocket socket;
	private InetAddress host;
	private DatagramSocket s;

	public RequestState(){
		this("192.168.145.130", 13375);
	}

	public RequestState(String ip, int p){
		serverIP = ip;
		port = p;
		netInit();
	}

	//Network Initialization
	private void netInit(){
		try {
			socket = new DatagramSocket();
			host = InetAddress.getByName(serverIP);
			s = new DatagramSocket(13378);
		} catch (Exception e) {
			System.out.println("Error in netInit().");
			System.out.println(e.getStackTrace());
		}
	}

	public HashMap<String, String> getState(){
		String[] pairs;
		HashMap<String, String> state = new HashMap<>();
		try {
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
	    	return state;
		} catch(Exception e){
			System.out.println("Error getting state.");
			System.out.println(e.getStackTrace());
			return null;
		}
	}

	public static void main(String [] args){
		RequestState rs = new RequestState();
		System.out.println(rs.getState().toString());
	}
}