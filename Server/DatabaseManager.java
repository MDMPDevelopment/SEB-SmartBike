import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.sql.SQLException;


public class DatabaseManager implements DatabaseManagerInterface{

	private Connection connection = null;
	private ResultSet resultSet = null;
	private Statement statement = null;
	private String DatabasePath = "./";
	private String DatabaseName;

	public static final String DEFAULT_DATABASE = "seb.db";


	public DatabaseManager(String dbName) {

		DatabaseName = dbName;

		try {
        		Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find SQLite driver.");
			e.printStackTrace();
		}

		try {
        		connection = DriverManager.getConnection("jdbc:sqlite:" + DatabasePath + DatabaseName);
        		statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Unable to create database connection.");
			e.printStackTrace();
		}
	}

	public DatabaseManager() {
		this(DEFAULT_DATABASE);
	}

	public void addMeasurement(String type, String data) {
		int MeasurementId = -1;
		//Set resultSet to all measurement types
		try {
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

		} catch (SQLException e) {
			System.out.println("Failed to add measurement.");
			e.printStackTrace();
		}
	}


	protected String getMeasurmentEntities() {
		String query = ""; //"EntityId|MeasurementId  |\tValue \t|\tTime\n";

		try {
			//Query the MeasurementEntities table
			resultSet = statement.executeQuery("SELECT * FROM MeasurementEntities");
			//Build the string
	        while (resultSet.next())
	        	query += resultSet.getString("EntityId") + "|"
	                + resultSet.getString("MeasurementId") + "|"
	                + resultSet.getString("Value");
	               // + resultSet.getString("TimeStamp") + "\n";
		} catch (SQLException e) {
			System.out.println("Could not get measurement entities.");
			e.printStackTrace();
		}
		return query;
	}

	private String getMeasurmentEntities(String type) {
		String query ="EntityId|MeasurementId  |\tValue \t|\tTime\n";

		try {
			//Query the MeasurementEntities table
			resultSet = statement.executeQuery("SELECT * FROM MeasurementEntities INNER JOIN Measurements ON MeasurementEntities.MeasurementId = Measurements.MeasurementId WHERE MeasurementType == \"" + type + "\"");
			//Build the string
	        while (resultSet.next())
	        	query += resultSet.getString("EntityId") + "\t|\t"
	                + resultSet.getString("MeasurementId") + "\t|\t"
	                + resultSet.getString("Value") + "\t|\t"
	                + resultSet.getString("TimeStamp") + "\n";
		} catch (SQLException e) {
			System.out.println("Could not get measurement entities.");
			e.printStackTrace();
		}
		return query;
	}


	private String getMeasurmentTypes() {
		String query ="ID \t|\tType \t|\tUnits \n";
		//Query the Measurements table

		try {
			resultSet = statement.executeQuery("SELECT * FROM Measurements");
	        //Build the string
	        while (resultSet.next())
	        	query += resultSet.getString("MeasurementId") + "\t|\t"
	                + resultSet.getString("MeasurementType") + "\t|\t"
	                + resultSet.getString("MeasurementUnits") + "\t|\t"
	                + resultSet.getString("UpdateDate") + "\n";

		} catch (SQLException e) {
			System.out.println("Could not get measurement types.");
			e.printStackTrace();
		}
		return query;
	}

	/*
	 * Return a HashMap of the current state of the system in the database
	 */
	public HashMap<String, String> getSystemState() {
		HashMap<String, String> state = new HashMap<>();

		try {
			resultSet = statement.executeQuery("SELECT Variable, State FROM SystemState;");

			while (resultSet.next()) {
				state.put(resultSet.getString("Variable"), resultSet.getString("State"));
			}

		} catch (SQLException e) {
			System.out.println("Could not get system state.");
			e.printStackTrace();
		}
		return state;
	}

	/*
	* Given the variable and its state, update the database
	*/
	public void setSystemState(String variable, String state) {

		String sqlStatement = "UPDATE SystemState " +  "SET State = ? " + "WHERE Variable = ? ";

		try {
			PreparedStatement prpstat = connection.prepareStatement(sqlStatement);
			prpstat.setString(1, state);
			prpstat.setString(2, variable);
			prpstat.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Could not update system state.");
			e.printStackTrace();
		}

	}



	public String getHistory() {
		String query = ""; //"EntityId|MeasurementId  |\tValue \t|\tTime\n";

		try {
			//Query the MeasurementEntities table
			resultSet = statement.executeQuery("SELECT * FROM History");
			//Build the string
	        while (resultSet.next())
	        	query += resultSet.getString("RideId") + "|"
	                + resultSet.getString("AverageSpeed") + "|"
									+ resultSet.getString("MaxSpeed") + "|"
									+ resultSet.getString("Distance") + "|"
	                + resultSet.getString("Duration");
	               // + resultSet.getString("TimeStamp") + "\n";
		} catch (SQLException e) {
			System.out.println("Could not get history.");
			e.printStackTrace();
		}
		return query;
	}

	/*
	 * Record Ride history in history table
	 * 1) Aggregate current entity table and write a row to History table for that aggregation
	 * 2) Clear current entity table to begin new ride
	 */
	public void newRide() {
		//"EntityId|MeasurementId  |\tValue \t|\tTime\n";
		String fromClause = "FROM MeasurementEntities " +
												"JOIN Measurements " +
												"ON MeasurementEntities.MeasurementId = Measurements.MeasurementId " +
												"WHERE MeasurementType = 'Speed'";
		try {
			resultSet = statement.executeQuery("SELECT AVG(Value) AS AverageSpeed " + fromClause);
	    double averageSpeed = resultSet.getDouble("AverageSpeed");

			resultSet = statement.executeQuery("SELECT MAX(Value) AS MaxSpeed " + fromClause);
			double maxSpeed = resultSet.getDouble("MaxSpeed");

			resultSet = statement.executeQuery("SELECT COUNT(*) AS SpeedEntries " + fromClause);
			double distance = (2*Math.PI*0.29) * 5 * resultSet.getDouble("SpeedEntries");
			resultSet = statement.executeQuery("SELECT JULIANDAY(MAX(Value)) - JULIANDAY(MIN(Value)) AS Duration " + fromClause);
			//double duration = resultSet.getDouble("Duration");
			double duration = 0;


		PreparedStatement statement;

		String insert = "INSERT INTO History (AverageSpeed, MaxSpeed, Distance, Duration) " +
										"VALUES (?, ?, ?, ?) ";
		statement = connection.prepareStatement(insert);
		statement.setDouble(1, averageSpeed);
		statement.setDouble(2, maxSpeed);
		statement.setDouble(3, distance);
		statement.setDouble(4, duration);
		statement.executeUpdate();

		String deleteME = "DELETE FROM MeasurementEntities ";
		statement = connection.prepareStatement(deleteME);
		statement.executeUpdate();

		String deleteSequence = "DELETE FROM sqlite_sequence WHERE name = 'MeasurementEntities' ";
		statement = connection.prepareStatement(deleteSequence);
		statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Could not get measurement entities.");
			e.printStackTrace();
		}
	}


	public void exit() {
		try {
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Could not update system state.");
			e.printStackTrace();
		}
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
