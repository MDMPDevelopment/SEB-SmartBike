package Network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseManager {
	
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
	
	
	public void exit() throws Exception {
		resultSet.close();
		statement.close();
		connection.close();
	}
	
    public static void main(String[] args) {
		try {
			DatabaseManager db = new DatabaseManager();
			System.out.println("Listing measurment types:");
			System.out.println(db.getMeasurmentTypes());
			System.out.println("Listing measurment entities:");
			System.out.println(db.getMeasurmentEntities());
			//System.out.println("Adding GPS reading...");
			//db.addMeasurement("Speed", "10");
			System.out.println("Listing measurment entities of type 'Speed':");
			System.out.println(db.getMeasurmentEntities("Speed"));
			
			db.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
