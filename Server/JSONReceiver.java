import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Inet4Address;
//import DatabaseManager.java;

public class JSONReceiver {
	private final int PACKETSIZE = 500;
	private DatabaseManager DbM;
	private DatagramSocket socket;
	private String serverIP;
	private int serverPort;
	
	
	public JSONReceiver() throws Exception {
		this(13375);
	}
	public JSONReceiver(int port) throws Exception {
		this("10.0.0.18", port);
	}
	public JSONReceiver(String serverIP, int serverPort) throws Exception {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.DbM = new DatabaseManager();
		socket = new DatagramSocket(serverPort);
	}

	
	private void packetReceived(DatagramPacket packet) throws Exception {
		String s = new String(packet.getData()).trim();
		String [] pairs = s.split(":");
		DbM.addMeasurement(pairs[0], pairs[1]);
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
			JSONReceiver jr = new JSONReceiver();
			jr.startReceiving();
		} catch (Exception e){
			System.out.println(e);
		}
	}

}

