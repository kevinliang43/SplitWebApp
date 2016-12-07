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

  /**
   * returns all group invites to this account.
   * @return an arraylist containing all GroupInvites to this Account.
   */
  public ArrayList<GroupInvite> getGroupInvites() {
    ArrayList<GroupInvite> groupList = new ArrayList<GroupInvite>();

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "SELECT * FROM groupInvite" +
                      " WHERE accountID = " + this.accountID + ";");

      while (resultSet.next()) {
        groupList.add(new GroupInvite(resultSet.getInt("groupID"),
                this, this.connection));
      }


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return groupList;
  }

  /**
   * Gets the top 5 recent expenses of this account
   *
   * @return arraylist containing the 5 most recent expenses.
   */
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

  /**
   * Gets the total owed to this account.
   *
   * @return integer containing the 5 most recent expenses.
   */
  public int totalOwed() {
    int total = 0;

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "CALL totalOwed(" + this.accountID  + ");");

      resultSet.next();

      total = resultSet.getInt("total");


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return total;
  }

  /**
   * Gets the total owed by this account.
   *
   * @return integer containing the 5 most recent expenses.
   */
  public int totalOwes() {
    int total = 0;

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "CALL totalOwes(" + this.accountID  + ");");

      resultSet.next();

      total = resultSet.getInt("total");


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return total;
  }

  /**
   * accepts an invite from a group.
   * Adds person to the groupList. Deletes the groupInvite. Both from the database.
   *
   * @param g represents the groupInvite.
   */
  public void acceptInvite(GroupInvite g) {
    if (!this.getGroupInvites().contains(g)) {
      throw new IllegalArgumentException("Cannot accept a group invite that does not exist. ");
    }

    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate(
              "INSERT INTO groupList (accountID, groupID) VALUES ("
                      + g.getField("accountID") + ", " + g.getField("groupID") + "); ");

      statement.executeUpdate(
              "DELETE FROM groupInvite WHERE groupID = "
                      + g.getField("groupID") +
                      " AND accountID = " + g.getField("accountID") + "; ");


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Sends an invite from a certain group to another account.
   *
   * @param group represents from which group you are sending the invite to.
   * @param account represents the account which you are sending the invite to.
   */
  public void sendInvite(Group group, Account account) {
    if (Integer.valueOf(group.getField("adminID")) != this.accountID) {
      throw new IllegalArgumentException("You are not the admin of this Group. ");
    }

    if (account.equals(this)) {
      throw new IllegalArgumentException("Cannot send an invite to yourself. ");
    }

    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate(
              "INSERT INTO groupInvite (groupID, accountID) VALUES ("
                      + group.getField("groupID") + ", " + account.getField("accountID") + "); ");


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }


  /**
   * declines an invite from a group. Can only decline a invite that actually exists.
   * @param group
   */
  public void declineInvite(GroupInvite group) {
    if (!this.getGroupInvites().contains(group)) {
      throw new IllegalArgumentException("Cannot decline an invite that doesn't exist");
    }

    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate(
              "DELETE FROM groupInvite WHERE groupID = "
                      + group.getField("groupID") + " AND accountID = " + this.getField("accountID") + "; ");


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  // add expense
  public void addExpense(Group group, int expenseID, List<Account> owes,
                         int amountOwed, boolean paid, String date, String expenseName) {
    if (!this.getGroupList().contains(group)) {
      throw new IllegalArgumentException("Cannot add expense to a group that youre not in. ");
    }

    if (group.getExpenses().contains(new Expense(expenseID, this.connection))) {
      throw new IllegalArgumentException("Cannot add an expense that already exists. ");
    }
    for (Account account : owes) {
      this.addSubExpense(expenseID,
              Integer.valueOf(group.getField("groupID")),
              Integer.valueOf(account.getField("accountID")),
              Integer.valueOf(this.getField("accountID")),
              amountOwed, paid, date, expenseName);
    }

  }

  // generate sub expense.

  private void addSubExpense(int expenseID, int groupID, int accountOwes, int accountOwed,
                             int amountOwed, boolean paid, String date, String expenseName) {

    try {
      Statement statement = this.connection.createStatement();
      statement.executeUpdate(
              "INSERT INTO expense " +
                      "(expenseID, groupID, accountOwes, accountOwed, amountOwed, paid, date, name) " +
                      "VALUES (" +
                      expenseID + ", " +
                      groupID + ", '" +
                      accountOwes + "', '" +
                      accountOwed + "', '" +
                      amountOwed + "', " +
                      paid + ", '" +
                      date + "', '" +
                      expenseName + "');");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  // delete expense
  public void deleteExpense(Group group, Expense expense) {

    if (!group.getExpenses().contains(expense)) {
      throw new IllegalArgumentException("Cannot delete an expense that does not exist. ");
    }

    if (!expense.getField("accountOwed").equals(this.getField("accountID"))) {
      throw new IllegalArgumentException("Cannot delete an expense that is not yours.");
    }



  }


  // update expense (paid/ not paid)

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



