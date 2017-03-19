import java.net.*;
import java.util.HashMap;
//import DatabaseManager.java;

public class Server {
	private final int PACKETSIZE = 500;
	private boolean running;
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
		this.running = false;
		socket = new DatagramSocket(serverPort);
	}

	public boolean getRunning() {
		return running;
	}
	public String getServerIP(){
		return serverIP;
	}
	public int getServerPort(){
		return serverPort;
	}

	private void packetReceived(DatagramPacket packet) throws Exception {
		String s = new String(packet.getData()).trim();
		String[] pairs = s.split(":");
		if(pairs.length <= 2 ){
			switch (pairs[0]){
				case "GPS"	:	DbM.addMeasurement(pairs[0], pairs[1]);
								DbM.setSystemState(pairs[0], pairs[1]);
								break;
				case "newRide": DbM.newRide();
							break;
				case "brake":	DbM.setSystemState(pairs[0],pairs[1]);
							break;
				case "turnL": 	DbM.setSystemState(pairs[0],pairs[1]);
							break;
				case "getState":sendState(packet.getAddress(), pairs[1]);
							break;
				case "turnR":	DbM.setSystemState(pairs[0],pairs[1]);
								break;
				default:		DbM.addMeasurement(pairs[0], pairs[1]);
								DbM.setSystemState(pairs[0], pairs[1]);
								break;
			}
			//System.out.println("Type: " + pairs[0] + "\nValue: " + pairs[1]);
		}
	}

	public void startReceiving() throws Exception {
		running = true;
		//System.out.println("Starting to receive");
		while(true){
			DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE );
			socket.receive(packet);
			this.packetReceived(packet);
		}
	}
	
	//Sends a copy of the system state to the ip + port that requested it
	//It will be sent in the following format:
	//"Speed:10 turnL:1 turnR:0 brake:1 "
	private void sendState(InetAddress host, String sport) throws Exception{
		//InetAddress host = InetAddress.getByName(ip);
		int port = Integer.parseInt(sport);
		DatagramSocket socket = new DatagramSocket();
		byte[] data;
		String s = "";
		//Iterate through the keys in the hash table building the message
		HashMap<String, String> state = DbM.getSystemState();
		for (String key : state.keySet()){
			s += key + ':' +state.get(key) + ' ';
		}
		data = s.getBytes();
        socket.send(new DatagramPacket(data, data.length, host, port));
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
			Server s = new Server();
			s.startReceiving();
		} catch (Exception e){
			System.out.println(e);
		}
	}
}
