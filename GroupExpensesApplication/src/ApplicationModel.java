import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by KevinLiang on 12/3/16.
 */



public class ApplicationModel {

  private Account login;
  private Connection connection;

  public ApplicationModel() {
    this.login = null;
    this.connection = null;
  }

  //establish a connection to the database.
  public void getConnection(String username, String password, String database) {
    String connectionURL = "jdbc:mysql://localhost:3306/" + database +
            "?autoReconnect=true&useSSL=false";

    Connection connect = null;

    try {
      connect = DriverManager.getConnection(connectionURL, username, password);
    } catch (SQLException e) {
      System.out.println("Unable to connect to the database. Please Try again: \n");
//      System.out.println(e.getMessage());
//      System.out.println(e.getErrorCode());
//      System.out.println(e.getSQLState());
    }
    this.connection = connect;

  }

  // close connection to the database.
  public void close() {

    if (this.connection == null) {
      throw new IllegalStateException(
              "Cannot disconnect from database when no connection has been established. ");
    }

      try {
        connection.close();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
  }

  // Login

  public void accountLogin(String username, String password) throws Exception {
    if (this.connection == null) {
      throw new IllegalStateException(
              "Cannot login to the Database when no connection has been established. ");
    }

    if (this.login != null) {
      throw new IllegalArgumentException(
              "Cannot attempt to login to different account, while already logged into one. ");
    }


    String passwordDB = "";
    int accountID = 0;

    // gets the account password and the accountID with the given username.
    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "SELECT accounts.password, accountID FROM accounts WHERE username = '" + username + "';");

      resultSet.first();

      passwordDB = resultSet.getString("password");
      accountID = resultSet.getInt("accountID");


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    // checks if account exists in database.
    if (passwordDB == "" || accountID == 0) {
      throw new Exception("No such account exists in database. ");
    }

    // checks if the password given is the same as the password in the database.
    if (!passwordDB.equals(password)) {
      throw new Exception("Password given does not match the password to the given Username. ");
    }

    // sets session with this account logged in
    this.login = new Account(accountID, this.connection);

  }

  public Account getLogin() {
    return login;
  }

  public Connection getConnection() {
    return connection;
  }

  // add account (cannot add if account already exists, autoincrement shouldnt be problem)

  // add group (cannot add if group already exists)

  // add expense (check if group exists first, accounts exist within group)

  // add groupInvite

  // add groupList (add group, remove group invite)

  // retrieve account info

  // retrieve group info

  // retrieve expense info

  // retrieve group Invite

  // retrieve groupList

}