import java.util.HashMap;
public interface DatabaseManagerInterface{
	public void addMeasurement(String type, String data) throws Exception;
<<<<<<< Updated upstream
	public HashMap<String, String> getSystemState() throws Exception;
=======
	public String getMeasurmentEntities() throws Exception;
	public String getMeasurmentEntities(String type) throws Exception;
	public String getMeasurmentTypes() throws Exception;
	> getSystemState() throws Exception;
>>>>>>> Stashed changes
	public void setSystemState(String variable, String state) throws Exception;
	public void newRide() throws Exception;
	public void exit() throws Exception;
}
