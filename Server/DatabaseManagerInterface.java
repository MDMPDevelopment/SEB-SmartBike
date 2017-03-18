import java.util.HashMap;
public interface DatabaseManagerInterface{
	public void addMeasurement(String type, String data);
	public HashMap<String, String> getSystemState();
	public void setSystemState(String variable, String state);
	public void newRide();
	public String getHistory();
	public void exit();
}
