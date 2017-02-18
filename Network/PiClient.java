import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Inet4Address;

public class PiClient {
	private DatagramSocket connection;
	private int localPort;
	private int bufferLength;
	private byte[] buffer;

	private String serverIP;
	private int serverPort;

	public PiClient() {
		this(false);
	}

	public PiClient(Boolean test) {
		this.serverIP = test ? "127.0.0.1": "10.0.0.18";
		//this.Inet4Address = new Inet4Address(IP);
		this.serverPort = serverPort;
		this.bufferLength = 576;
		this.buffer = new byte[this.bufferLength];
	}

	private DatagramSocket connectServer() {
		DatagramPacket connectionPacket = new DatagramPacket(this.buffer, this.bufferLength);

		this.connection = new DatagramSocket(this.localPort);
		this.connection.connect(this.serverIP, this.serverPort);

		this.connection.receive(connectionPacket);

		this.connection.disconnect();
		this.connection.close();
		return connection;
	}

	private void setPort(int newPort) {
		this.serverPort = newPort;
	}
}
