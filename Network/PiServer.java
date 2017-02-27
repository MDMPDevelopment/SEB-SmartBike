package Network;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import Network.JSONReceiver;
import Network.DatabaseManager;

public class PiServer {
	private JSONReceiver rcv;
	private DatabaseManager db;

	private String data;
	private Boolean running;

	public PiServer() {
		try {
			rcv = new JSONReceiver();
			db = new DatabaseManager();
		} catch (Exception e) {
			System.out.println(e);
		}

		data = "";
		running = false;
	}

	public void startServer() {
		rcv.start();

		running = true;

		runServer();
	}

	public void exit() {
		try {
			rcv.exit();
		} catch (Exception e) {
			System.out.println(e);
		}

		running = false;
	}

	private void runServer() {
		while (running) {
			if (rcv.newData()) {
				data = rcv.readData();
				System.out.println(data);  // Placeholder until DatabaseManager can push data to the database.
			}
		}
	}

	public static void main(String[] args) {
		PiServer server = new PiServer();
		server.startServer();

		server.exit();
	}
}
