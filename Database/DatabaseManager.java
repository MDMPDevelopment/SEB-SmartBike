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
		
		String s = "INSERT INTO MeasurementEntities (MeasurementId, Value) " 
						+ "VALUES (" + MeasurementId + ", " + data + ")";
		statement.executeUpdate(s);
	}
	
	/*
	*public String getMeasurmentEntities(){
	*	String query ="ID \t|\tType \t|\tUnits \n"; 
	*	try {
	*	resultSet = statement.executeQuery("SELECT * FROM MeasurementEntities");
    *    while (resultSet.next()) 
    *    	query += "Id:"
    *            + resultSet.getString("MeasurementId") + "\t|\t"
    *            + resultSet.getString("") + "\t|\t"
    *            + resultSet.getString("MeasurementUnits")+ "\n";
    *    } catch (Exception e) {
	*		e.printStackTrace();
	*	}
	*	return query;
	*}
	*/
	
	public String getMeasurmentTypes()  throws Exception {
		String query ="ID \t|\tType \t|\tUnits \t|\t Time\n"; 
		resultSet = statement.executeQuery("SELECT * FROM Measurements");
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
		try{
			DatabaseManager db = new DatabaseManager();
			System.out.println("Listing measurment types:");
			System.out.println(db.getMeasurmentTypes());
			db.addMeasurement("Speed", "21");
			db.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
