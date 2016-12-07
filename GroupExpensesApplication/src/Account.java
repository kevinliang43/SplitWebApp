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

  /**
   * Updates the first name of this account.
   *
   * @param newName represents the new first name of the account.
   */
  public void updateFirstName(String newName) {
    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate(
              "UPDATE accounts " +
                      "SET firstName ='" + newName +"' " +
                      "WHERE accountID = " + this.accountID + ";");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // update lastName

  /**
   * Updates the last name of this account.
   *
   * @param newName represents the new last name of the account.
   */
  public void updateLastName(String newName) {
    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate(
              "UPDATE accounts " +
                      "SET lastName ='" + newName +"' " +
                      "WHERE accountID = " + this.accountID + ";");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // update password

  /**
   * Updates the password of this account on the given conditions:
   * 1. the oldPassword matches the current one.
   * 2. the newPassword and the confirmation password match.
   *
   * @param oldPassword represents the old password of this account to check against the current.
   * @param newPassword represents the new password (to be changed to).
   * @param confirm represents the new password, acts as a confirmation.
   */
  public void updatePassword(String oldPassword, String newPassword, String confirm) {
    if (!newPassword.equals(confirm)) {
      throw new IllegalArgumentException("New Password and confirmation password do not match. ");
    }

    if (!this.getField("password").equals(oldPassword)) {
      throw new IllegalArgumentException("Current password does not match the one given. ");
    }

    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate(
              "UPDATE accounts " +
                      "SET password ='" + newPassword +"' " +
                      "WHERE accountID = " + this.accountID + ";");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  /////////////////////////////////Account Functions//////////////////////////////////

  // get the group list of this person

  /**
   * returns all Groups that this Account is apart of.
   *
   * @return an ArrayList of all groups that this account is a part of.
   */
  public ArrayList<Group> getGroupList() {

    ArrayList<Group> groupList = new ArrayList<Group>();

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "SELECT groupID FROM groupList WHERE accountID = " + this.accountID +";");

      while (resultSet.next()) {
        groupList.add(new Group(resultSet.getInt("groupID"), this.connection));
      }


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return groupList;
  }

  // get the group invites of this person

  

  // get all expenses owed to this person (by group or all)

  public ArrayList<Expense> recent5Expense() {
    ArrayList<Expense> recentExpenseList = new ArrayList<Expense>();

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "CALL top_expenses(" + this.accountID  + ");");

      while (resultSet.next()) {
        recentExpenseList.add(new Expense(resultSet.getInt("expenseID"), this.connection));
      }


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return recentExpenseList;

  }


  // get all expenses owed by this person (by group or all)




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



