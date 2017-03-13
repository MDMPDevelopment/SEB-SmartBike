import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//import org.junit.*;

public class DatabaseManagerTest {

  //public TestDatabaseManager() {
//  }
  private DatabaseManager db;

  public void setup() throws Exception{
    db = new DatabaseManager();
  }

  public void testAddMeasurement() {}
  public void testGetMeasurementEntities() {}
  public void testGetMeasurementEntitiesGivenType() {}
  public void testGetMeasurmentTypes() {}
  public void testGetSystemState() {}
  public void testSetSystemState() {}
  public void testNewRide() {}


  public void tearDown() throws Exception{
    db.exit();
  }


  public static void main(String args[]) {
    DatabaseManagerTest test = new DatabaseManagerTest();

    try {
      test.setup();




      test.tearDown();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
