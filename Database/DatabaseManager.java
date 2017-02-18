import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseManager {
	
	private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    
	private final String DatabasePath = "/home/pi/Desktop/SEB/";
    private final String DatabaseName = "seb.db";
	
	
	public DatabaseManager() {
		 try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabasePath + DatabaseName);
            statement = connection.createStatement();            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public void addMeasurement(String type, String data) {
		statement = "INSERT INTO MeasurementEntities (ID,NAME,AGE,ADDRESS,SALARY) " +
	}
	
	/*
	public String getMeasurmentEntities(){
		String query ="ID \t|\tType \t|\tUnits \n"; 
		try {
		resultSet = statement.executeQuery("SELECT * FROM MeasurementEntities");
        while (resultSet.next()) 
        	query += "Id:"
                + resultSet.getString("MeasurementId") + "\t|\t"
                + resultSet.getString("") + "\t|\t"
                + resultSet.getString("MeasurementUnits")+ "\n";
        } catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
	*/
	public String getMeasurmentTypes(){
		String query ="ID \t|\tType \t|\tUnits \n"; 
		try {
		resultSet = statement.executeQuery("SELECT * FROM Measurements");
        while (resultSet.next()) 
        	query += "Id:"
                + resultSet.getString("MeasurementId") + "\t|\t"
                + resultSet.getString("MeasurementType") + "\t|\t"
                + resultSet.getString("MeasurementUnits")+ "\n";
        } catch (Exception e) {
			e.printStackTrace();
		}
		return query;
	}
	
	public void exit(){
        try {
			resultSet.close();
			statement.close();
			connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
    public static void main(String[] args) {
		DatabaseManager db = new DatabaseManager();
		System.out.println(db.getMeasurmentTypes());
		db.exit();
    }
}
