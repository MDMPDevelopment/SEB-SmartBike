import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.net.*;
//import org.junit.framework.*;
//import static junit.Assert.*;
//extends TestCase

public class TestServer implements DatabaseManagerInterface {
	private ServerThread st;
	private DatagramSocket socket;
	private InetAddress host;
	private int port;
	private boolean testMeasurementAdded;

	protected void setUp() throws Exception{
		this.setUp("172.0.0.1", 13375);
	}

	protected void setUp(String host, int port) throws Exception{
		this.st = new ServerThread(host, port, this);
		st.start();
		this.host = InetAddress.getByName(host);
		this.port = port;
		this.socket = new DatagramSocket();
	}

	public void testSpeed() throws Exception{
		testMeasurementAdded = false;
		byte[] data = {'s', 'p', 'e', 'e', 'd', ':', '2', '7'};
		socket.send(new DatagramPacket(data, data.length, host, port));
		TimeUnit.SECONDS.sleep(5);
		assert testMeasurementAdded;
	}

	public void testTurnLights(){
		assert false;
	}

	public void runAllTests() throws Exception{
		System.out.print("Running 'testSpeed'...");
		this.testSpeed();
		System.out.println("\tPassed!");

		System.out.print("Running 'testTurnLights'...");
		this.testTurnLights();
		System.out.println("\tPassed!");
	}

	//
	public void addMeasurement(String type, String data) throws Exception{
		//assert
		testMeasurementAdded = true;
	}

	public String getMeasurmentEntities() throws Exception{
		return null;
	}

	public String getMeasurmentEntities(String type) throws Exception{
		return null;
	}

	public String getMeasurmentTypes() throws Exception{
		return null;
	}

	public HashMap<String, String> getSystemState() throws Exception{
		return null;
	}

	public void setSystemState(HashMap<String, String> state) throws Exception{

	}

	public void newRide() throws Exception{

	}
private Server s;
	
	public void exit() throws Exception{

	}

	public static void main(String[] args){
		try{
			TestServer ts = new TestServer();
			ts.setUp("192.168.145.130", 13375);
			ts.runAllTests();
		} catch (Exception e) {
			System.out.println("Tests failed...");
		}
	}

	public class ServerThread extends Thread {
		private Server s;
		public ServerThread(String host, int port, DatabaseManagerInterface DbM) throws Exception {
			this.s = new Server(host, port, DbM);
		}
		public void run() {
	    	//System.out.println("MyThread running");
			try{ 
				this.s.startReceiving(); 
			} catch (Exception e) {
				System.out.println("Uhhh... Something broke...");	
			}
		}
	}
}


