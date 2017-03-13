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
		//set the bool to false, send a speed reading, wait then assert it set the bool
		testMeasurementAdded = false;
		byte[] data = {'S', 'p', 'e', 'e', 'd', ':', '2', '7'};
		socket.send(new DatagramPacket(data, data.length, host, port));
		TimeUnit.SECONDS.sleep(1);
		assert testMeasurementAdded;
	}

	public void testTurnLights() throws Exception {
		//set the bool to false, send a turn reading, wait then assert it set the bool
		testMeasurementAdded = false;
		byte[] data = {'t', 'u', 'r', 'n', 'L', ':', '1'};
		socket.send(new DatagramPacket(data, data.length, host, port));
		TimeUnit.SECONDS.sleep(1);
		assert testMeasurementAdded;
	}

	public void testNewRide() throws Exception {
		testMeasurementAdded = false;
		byte[] data = {'n', 'e', 'w', 'R', 'i', 'd', 'e', ':', '1'};
		socket.send(new DatagramPacket(data, data.length, host, port));
		TimeUnit.SECONDS.sleep(1);
		assert testMeasurementAdded;
	}

	public void testFaulty() throws Exception {
		byte[] data = {'f', 'a', 'u', 'l', 't'};
		socket.send(new DatagramPacket(data, data.length, host, port));
		TimeUnit.SECONDS.sleep(1);
	}

	public void runAllTests() throws Exception{
		System.out.print("Testing 'testSpeed'...");
		this.testSpeed();
		System.out.println("\t\tPassed!");

		System.out.print("Testing 'testTurnLights'...");
		this.testTurnLights();
		System.out.println("\tPassed!");

		System.out.print("Testing 'newRide'...");
		this.testNewRide();
		System.out.println("\t\tPassed!");

		System.out.print("Testing a faulty reading...");
		this.testFaulty();
		System.out.println("\tPassed!");
	}

	//The server will receive UDP packets and it has to send the correct data to the server
	//The code will assert that the server added a measurement and that it sent the set system state the correct data
	public void addMeasurement(String type, String data) throws Exception{
		testMeasurementAdded = true;
	}
	public void setSystemState(String variable, String state) throws Exception{
		if(variable.equals("Speed")) assert state.equals("27");
		if(variable.equals("turnL")) assert state.equals("1");
	}
	public void newRide() throws Exception{
		testMeasurementAdded = true;	
	}
	
	//The server doesn't use these methods, they are just here to match the interface
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
	public void exit() throws Exception{
		st.exit();
	}

	public static void main(String[] args){
		try{
			TestServer ts = new TestServer();
			ts.setUp("192.168.145.130", 13375);
			ts.runAllTests();
			ts.exit();
		} catch (Exception e) {
			System.out.println("Tests failed...");
			System.out.println(e);
		}
	}

	public class ServerThread extends Thread {
		private Server s;
		public ServerThread(String host, int port, DatabaseManagerInterface DbM) throws Exception {
			this.s = new Server(host, port, DbM);
		}

		public void exit() throws Exception{
			s.exit();
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
