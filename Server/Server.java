import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Inet4Address;
//import DatabaseManager.java;

public class Server {
	private final int PACKETSIZE = 500;
	private DatabaseManagerInterface DbM;
	private DatagramSocket socket;
	private String serverIP;
	private int serverPort;
	
	
	public Server() throws Exception {
		this(13375);
	}

	public Server(int port) throws Exception {
		this("192.168.145.130", port);
	}

	public Server(String serverIP, int serverPort) throws Exception {
		this(serverIP, serverPort, new DatabaseManager());
	}

	public Server(String serverIP, int serverPort, DatabaseManagerInterface DbM) throws Exception {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.DbM = DbM;
		socket = new DatagramSocket(serverPort);
	}
	
	private void packetReceived(DatagramPacket packet) throws Exception {
		String s = new String(packet.getData()).trim();
		String[] pairs = s.split(":");
		if(pairs.length != 2 ){
			System.out.println("Packet had an incorrect format!");
		} else {
			DbM.addMeasurement(pairs[0], pairs[1]);
			switch (pairs[0]){
				case "speed":	//handle speed data
								break;
				case "GPS"	:	//handle GPS data
								break;
				case "turnL":	//handle left turn
								break;
				case "turnR":	//handle right turn
								break;
				case "brake":	//handle brake
								break;
			}
		}
			
		System.out.println("Type: " + pairs[0] + "\nValue: " + pairs[1]);
	}

	public void startReceiving() throws Exception {
		while(true){
			DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE );
			socket.receive(packet);
			this.packetReceived(packet);
		}
	}

	public void setPort(int newPort) throws Exception {
		this.serverPort = newPort;
	}

	public void exit() throws Exception {
		this.socket.disconnect();
		this.socket.close();
	}

	public static void main(String [] args){
		try {
			Server jr = new Server();
			jr.startReceiving();
		} catch (Exception e){
			System.out.println(e);
		}
	}

}

