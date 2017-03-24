import java.util.HashMap;
import java.util.Random;

public class RequestStateStub implements  RequestStateInterface{

	private HashMap<String, String> state;
	private Integer counter;
	private Random randoCalrissian;

	public RequestStateStub(){
		counter = 0;
		state = new HashMap<String, String>();
		state.put("turnL", "0");
		state.put("turnR", "0");
		state.put("brake", "0");
		state.put("Speed", "0");

		randoCalrissian = new Random();

	}
	//Returns the a stubbed state increasing the speed by one each time.
	public HashMap<String, String> getState(){
		counter ++;
		//state.put("Speed", counter.toString());
		state.put("Speed", Double.toString(30*randoCalrissian.nextDouble()));

		state.put("turnL", randoCalrissian.nextBoolean() ? "1" : "0");
		state.put("turnR", randoCalrissian.nextBoolean() ? "1" : "0");
		state.put("brake", randoCalrissian.nextBoolean() ? "1" : "0");

		return state;
	}
}
