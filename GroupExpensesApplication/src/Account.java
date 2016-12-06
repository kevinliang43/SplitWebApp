/**
 * Created by KevinLiang on 11/15/16.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    if (!this.containsField(fieldName)) {
      throw new IllegalArgumentException("field given does not exist. ");
    }

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


  /**
   * checks if the given field Name exists in the accounts.
   * @param fieldName represents the fieldName to be checked.
   * @return boolean representing if the field name exists.
   */
  public boolean containsField(String fieldName) {
    return this.getFieldNames().contains(fieldName);
  }

  /**
   * Gets all the field Names of an account and stores it in an ArrayList.
   *
   * @return an arrayList with all fieldNames.
   */
  public ArrayList<String> getFieldNames() {

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
    return fieldNames;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Account account = (Account) o;

    return accountID == account.accountID;

  }

  @Override
  public int hashCode() {
    return accountID;
  }
}



