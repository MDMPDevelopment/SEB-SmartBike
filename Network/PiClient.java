import java.net.Socket;

public class PiClient {
	private Socket server;
	private String serverIP;
	private int serverPort;

	public PiClient() {
		this(false);
	}

	public PiClient(Boolean test) {
		this.serverIP = test ? "127.0.0.1": "10.0.0.14";
		this.serverPort = serverPort;
	}

	private Socket connectServer() {
		Socket  connection = new Socket();
		
		return connection;
	}
}
