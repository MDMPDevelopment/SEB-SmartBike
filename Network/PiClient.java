import java.net.Socket;

public class PiClient {
	private Socket server;
	private String serverIP;
	private int serverPort;

	public PiClient(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	private Socket connectServer() {
		Socket  connection = new Socket();
		return connection;
	}
}
