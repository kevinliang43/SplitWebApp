import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by KevinLiang on 12/6/16.
 */
public class Expense {

  private int expenseID;
  private Connection connection;

  public Expense(int expenseID, Connection connection) {
    this.expenseID = expenseID;
    this.connection = connection;
  }

  /**
   * Gets the row value for the specified field of this expense.
   *
   * @param fieldName represents the field required.
   * @return a String of the row value.
   */
  public ArrayList<String> getField(String fieldName) {


    ArrayList<String> fields = new ArrayList<String>();

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "SELECT * FROM expense " +
                      "WHERE expenseID = " + this.expenseID + ";");

      while (resultSet.next()) {
        fields.add(resultSet.getString(fieldName));
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return fields;
  }

  /**
   * Returns all subexpenses that make up this expense.
   *
   * @return
   */
  public ArrayList<SubExpense> getSubExpenses() {

    ArrayList subExpenses = new ArrayList<SubExpense>();

    try {
      Statement statement = this.connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
              "SELECT * FROM expense " +
                      "WHERE expenseID = " + this.expenseID + ";");

      while (resultSet.next()) {
        subExpenses.add(new SubExpense(
                resultSet.getInt("expenseID"),
                new Account(resultSet.getInt("accountOwes"), this.connection),
                this.connection));
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return subExpenses;
  }

  public ArrayList<Account> getAccountsOwes() {
    this.getField("")
  }

  /**
   * returns the the String version of this Expense.
   *
   * @return a String representing this subexpense.
   */
  public String toString() {

    Account accountOwed = new Account(Integer.valueOf(this.getField("accountOwed")), this.connection);
    Account accountOwes = new Account(Integer.valueOf(this.getField("accountOwes")), this.connection);

    String output = "Expense #" + this.getField("expenseID") +": " + this.getField("name") + "\n";
    output += "Date: " + this.getField("date") + "\n";
    output += "Person Owed: " + accountOwed.getField("firstName") + " " + accountOwed.getField("lastName") + "\n";
    output += "Person Owes: " + accountOwes.getField("firstName") + " " + accountOwes.getField("lastName") + "\n";
    output += "Amount : $" + this.getField("amountOwed") + "\n";
    output += "Status: ";
    if (Integer.valueOf(this.getField("paid")) == 0) {
      output += "Not Paid";
    }
    else {
      output += "Paid" + "\n";
    }

    return output;

  }



}
