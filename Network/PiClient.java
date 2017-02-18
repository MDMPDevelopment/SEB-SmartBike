import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Inet4Address;

public class JSONReceiver {
	private DatagramSocket socket;
	private int localPort;
	private int bufferLength;
	private byte[] buffer;
	
	private String serverIP;
	private int serverPort;
	
	

	public JSONReceiver() {
		this(13375);
	}

	public JSONReceiver(int port){
		this("10.0.0.18", port);
	}

	public PiClient(String serverIP, int serverPort) {
		//this.serverIP = test ? "127.0.0.1": "10.0.0.18";
		//this.Inet4Address = new Inet4Address(IP);
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.bufferLength = 576;
		this.buffer = new byte[this.bufferLength];
		socket = new DatagramSocket(ServerPort);
		
	}

	


	private DatagramPacket connectServer() {
		DatagramPacket packet = new DatagramPacket(this.buffer, this.bufferLength);


		this.socket.receive(packet);

		this.socket.disconnect();
		this.socket.close();
		return packet;
	}

	private void setPort(int newPort) {
		this.serverPort = newPort;
	}
}
