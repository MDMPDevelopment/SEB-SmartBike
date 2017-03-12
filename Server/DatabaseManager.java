import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;


public class DatabaseManager implements DatabaseManagerInterface{

	private Connection connection = null;
	private ResultSet resultSet = null;
	private Statement statement = null;
	private final String DatabasePath = "./";
	private final String DatabaseName = "seb.db";


	public DatabaseManager() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + DatabasePath + DatabaseName);
        statement = connection.createStatement();
	}

	public void addMeasurement(String type, String data)  throws Exception {
		int MeasurementId = -1;
		//Set resultSet to all measurement types
		resultSet = statement.executeQuery("SELECT * FROM Measurements");
		//Map the type to an ID
		while (resultSet.next())
			if( resultSet.getString("MeasurementType").equals(type))
				MeasurementId = (int) Integer.parseInt(resultSet.getString("MeasurementId"));
		//IF we didn't find the type error out
		if(MeasurementId < 0) {
			System.out.println("ERROR: type not found: " + resultSet.getString("MeasurementType"));
			return;
		}
		//This will build the query
		String s = "INSERT INTO MeasurementEntities (MeasurementId, Value) "
						+ "VALUES (" + MeasurementId + ", " + "\"" + data + "\")";
		//This will execute the query
		statement.executeUpdate(s);
	}


	public String getMeasurmentEntities() throws Exception {
		String query ="EntityId|MeasurementId  |\tValue \t|\tTime\n";
		//Query the MeasurementEntities table
		resultSet = statement.executeQuery("SELECT * FROM MeasurementEntities");
		//Build the string
        while (resultSet.next())
        	query += resultSet.getString("EntityId") + "\t|\t"
                + resultSet.getString("MeasurementId") + "\t|\t"
                + resultSet.getString("Value") + "\t|\t"
                + resultSet.getString("TimeStamp") + "\n";
		return query;
	}

	public String getMeasurmentEntities(String type) throws Exception {
		String query ="EntityId|MeasurementId  |\tValue \t|\tTime\n";
		//Query the MeasurementEntities table
		resultSet = statement.executeQuery("SELECT * FROM MeasurementEntities INNER JOIN Measurements ON MeasurementEntities.MeasurementId = Measurements.MeasurementId WHERE MeasurementType == \"" + type + "\"");
		//Build the string
        while (resultSet.next())
        	query += resultSet.getString("EntityId") + "\t|\t"
                + resultSet.getString("MeasurementId") + "\t|\t"
                + resultSet.getString("Value") + "\t|\t"
                + resultSet.getString("TimeStamp") + "\n";
		return query;
	}


	public String getMeasurmentTypes()  throws Exception {
		String query ="ID \t|\tType \t|\tUnits \n";
		//Query the Measurements table
		resultSet = statement.executeQuery("SELECT * FROM Measurements");
        //Build the string
        while (resultSet.next())
        	query += resultSet.getString("MeasurementId") + "\t|\t"
                + resultSet.getString("MeasurementType") + "\t|\t"
                + resultSet.getString("MeasurementUnits") + "\t|\t"
                + resultSet.getString("UpdateDate") + "\n";
		return query;
	}

	/*
	 * Return a HashMap of the current state of the system in the database
	 */
	public HashMap<String, String> getSystemState() throws Exception {
		HashMap<String, String> state = new HashMap<>();

		resultSet = statement.executeQuery("SELECT Variable, State FROM SystemState;");

		while (resultSet.next()) {
			state.put(resultSet.getString("Variable"), resultSet.getString("State"));
		}

		return state;
	}

  /*
	 * Given the variable and its state, update the database
   */
	public void setSystemState(String variable, String state) throws Exception {

		String sqlStatement = "UPDATE SystemState " +
												  "SET State = ? " +
													"WHERE Variable = ? ";

		PreparedStatement prpstat = connection.prepareStatement(sqlStatement);
		prpstat.setString(1, state);
		prpstat.setString(2, variable);
		prpstat.executeUpdate();

	}

	/*
	 * Record Ride history in history table
	 * 1) Aggregate current entity table and write a row to History table for that aggregation
	 * 2) Clear current entity table to begin new ride
	 */
  public void newRide() {

		// *TODO* This query is not correct, this method needs some work
		String sqlStatement = "INSERT INTO History " +
													"(AverageSpeed, MaxSpeed, Distance, Duration) " +
													"SELECT AVG(Value) AS AverageSpeed " +
													"			 ,MAX(Value) AS MaxSpeed " +
													"			 ,0 AS Distance " +
													"			 ,0 AS Duration " +
													"FROM MeasurementEntities me " +
													"JOIN Measurements m " +
													"ON m.MeasurementId = me.MeasurementId " +
													"WHERE m.MeasurementType = ? ";

	}


	public void exit() throws Exception {
		resultSet.close();
		statement.close();
		connection.close();
	}


    public static void main(String[] args) {
		try {
			DatabaseManager db = new DatabaseManager();
			//System.out.println("Listing measurment types:");
			//System.out.println(db.getMeasurmentTypes());
			//System.out.println("Listing measurment entities:");
			//System.out.println(db.getMeasurmentEntities());
			//System.out.println("Adding GPS reading...");
			//db.addMeasurement("Speed", "10");
			//ystem.out.println("Listing measurment entities of type 'Speed':");
			//System.out.println(db.getMeasurmentEntities("Speed"));

			HashMap<String, String> state;

			db.setSystemState("turnL", "0");

			System.out.println("--- SYSTEM STATE---");
			state = db.getSystemState();
			for (String key: state.keySet()) {
				System.out.println(key + " : " + state.get(key));
			}


			db.setSystemState("turnL", "1");


			System.out.println("--- SYSTEM STATE---");
			state = db.getSystemState();
			for (String key: state.keySet()) {
				System.out.println(key + " : " + state.get(key));
			}





			db.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
