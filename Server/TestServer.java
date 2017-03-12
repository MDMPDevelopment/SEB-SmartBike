import java.util.HashMap;
import junit.framework.*;

public class TestServer implements DatabaseManagerInterface extends TestCase{
	private Server s;
	private DatagramSocket socket;
	private InetAddress host;
	private int port;

	protected void setUp() throws Exception{
		this.setUp("127.0.0.1", 13375);
	}

	protected void setUp(String host, int port) throws Exception{
		this.s = new Server(host, port, this);
		this.host = InetAddress.getByName(host);
		this.port = port;
		this.socket = new DatagramSocket();
	}

	public void testSpeed(){
		byte[] data = "speed:27";
		socket.send(new DatagramPacket(data, data.length, host, port));
		
	}


	//
	public void addMeasurement(String type, String data) throws Exception{

	}

	public String getMeasurmentEntities() throws Exception{

	}

	public String getMeasurmentEntities(String type) throws Exception{

	}

	public String getMeasurmentTypes() throws Exception{

	}

	public HashMap<String, String> getSystemState() throws Exception{

	}

	public void setSystemState(HashMap<String, String> state) throws Exception{

	}

	public void newRide() throws Exception{

	}

	public void exit() throws Exception{

	}
}