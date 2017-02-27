import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import JSONReceiver;
import DatabaseManager;

public class PiClient {
	private JSONReceiver rcv;
	private DatabaseManager db;

	public PiClient() {
		rcv = new JSONReceiver();
		db = new DatabaseManager();
	}
}
