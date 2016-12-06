/**
 * Created by KevinLiang on 11/15/16.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an account in the application.
 */
public class Account {

  private int accountID;
  private Connection connection;

  public Account(int accountID, Connection connection) {
    this.accountID = accountID;
    this.connection = connection;
  }


  /**
   * Gets the row value for the specified field of this account.
   *
   * @param fieldName represents the field required.
   * @return a String of the row value.
   */
  public String getField(String fieldName) {

    String field = "";

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "SELECT * FROM accounts WHERE accountID = " + this.accountID + ";");

      resultSet.next();
      field = resultSet.getString(fieldName);

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return field;
  }

  // check if field exists

  /**
   * Checks if the fieldName string matches any of the fieldNames that exist in
   * @param fieldName
   * @return
   */
  public boolean checkField(String fieldName) {

    ArrayList<String> fieldNames = new ArrayList<String>(5);

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "SELECT * FROM accounts WHERE accountID = 0;");
      ResultSetMetaData metaData = resultSet.getMetaData();

      for (int i = 1; i < metaData.getColumnCount(); i++) {
        fieldNames.add(metaData.getColumnName(i));
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    System.out.println(fieldNames);
    return fieldNames.contains(fieldName);
  }

}



