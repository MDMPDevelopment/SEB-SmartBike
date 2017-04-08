import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.sql.SQLException;
import java.io.*;


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
			logException(e);
		}

		try {
        		connection = DriverManager.getConnection("jdbc:sqlite:" + DatabasePath + DatabaseName);
        		statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Unable to create database connection.");
			logException(e);
		}

	}

	public DatabaseManager() {
		this(DEFAULT_DATABASE);
	}

	private void logException(Exception e) {
		try {
			FileWriter fileWriter = new FileWriter("log.txt", true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter printWriter = new PrintWriter(bufferedWriter, true);
			e.printStackTrace(printWriter);
			fileWriter.close();
			bufferedWriter.close();
			printWriter.close();
		} catch (IOException ioe) {
			throw new RuntimeException("Could not log exception", ioe);
		}
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
			logException(e);
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
			logException(e);
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
			logException(e);
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
			logException(e);
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
			logException(e);
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
			logException(e);
		}
	}


	public void exit() {
		try {
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Could not update system state.");
			logException(e);
		}
	}

}
