import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import java.util.HashMap;

public class DatabaseManagerTest {

  private DatabaseManager db;
  private String DatabaseName = "sebTEST.db";
  private String DatabasePath = "./";
  private Connection connection = null;

  //Initial Setup
//  public DatabaseManagerTest() {

//  }

  public void setup() {

    db = new DatabaseManager(DatabaseName);

    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      System.out.println("Could not find SQLite driver.");
      e.printStackTrace();
    }

    try {
      connection = DriverManager.getConnection("jdbc:sqlite:" + DatabasePath + DatabaseName);
    } catch (SQLException e) {
      System.out.println("Could not create database connection.");
      e.printStackTrace();
    }

    executeSQLHelper("UPDATE SystemState SET State = 0 ");
    executeSQLHelper("DELETE FROM MeasurementEntities  ");
    executeSQLHelper("DELETE FROM History ");
    executeSQLHelper("DELETE FROM sqlite_sequence WHERE name IN ('MeasurementEntities', 'History') ");
  }

  private void executeSQLHelper(String sql) {

    try {
		    PreparedStatement statement = connection.prepareStatement(sql);
		  statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Test setup failed.");
      e.printStackTrace();
    }
  }

  public void testAddMeasurement() {
    setup();
    db.addMeasurement("Speed", "15");
    Assert.assertEquals(db.getMeasurmentEntities(), "1|1|15\n");
    tearDown();
  }

  public void testGetSetSystemState() {
    setup();

    db.setSystemState("turnL", "1");
    db.setSystemState("brake", "1");
    db.setSystemState("Speed", "25");

    HashMap<String, String> state = db.getSystemState();

    Assert.assertEquals(state.get("turnL"), "1");
    Assert.assertEquals(state.get("brake"), "1");
    Assert.assertEquals(state.get("Speed"), "25");
    Assert.assertEquals(state.get("Distance"), "0");
    Assert.assertEquals(state.get("turnR"), "0");

    tearDown();
  }

  public void testNewRide() {}




  public void tearDown() {
    db.exit();

    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Could not update system state.");
      e.printStackTrace();
    }
  }


  public static void main(String args[]) {
    DatabaseManagerTest test = new DatabaseManagerTest();

    test.testAddMeasurement();
    test.testGetSetSystemState();

    System.out.println("Test Complete.");

  }

}
