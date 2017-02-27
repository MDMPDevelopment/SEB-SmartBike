package Network;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Inet4Address;

public class JSONReceiver implements Runnable {
	private final int PACKETSIZE = 500;

	private DatagramSocket socket;

	private String serverIP;
	private String data;

	private int serverPort;

	private Boolean newData;
	private Boolean running;

	private Thread t;

	public JSONReceiver() throws Exception {
		this(13375);
	}
	public JSONReceiver(int port) throws Exception {
		this("10.0.0.18", port);
	}
	public JSONReceiver(String serverIP, int serverPort) throws Exception {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		newData = false;
		running = false;
		data = "";
		socket = new DatagramSocket(serverPort);
	}

	
	private void packetReceived(DatagramPacket packet) throws Exception {
		this.data = new String(packet.getData()).trim();
		this.newData = true;
	}

	public String readData() {
		String data = this.data;
		this.data = "";
		this.newData = false;
		return this.data;
	}

	public Boolean newData() {
		return this.newData;
	}

	public void startReceiving() throws Exception {
		while(running){
			DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE );
			socket.receive(packet);
			this.packetReceived(packet);
		}
	}

	public void setPort(int newPort) throws Exception {
		this.serverPort = newPort;
	}

	public void run() {
		try {
			running = true;
			this.startReceiving();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "JSONReceiver");
			t.start();
		}
	}

	public void exit() throws Exception {
		this.running = false;
		this.socket.disconnect();
		this.socket.close();
	}

	public static void main(String [] args){
		try {
			JSONReceiver jr = new JSONReceiver();
			jr.startReceiving();
		} catch (Exception e){
			System.out.println(e);
		}
	}

}
