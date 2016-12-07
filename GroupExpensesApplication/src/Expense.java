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

  public Expense(int exepnseID, Connection connection) {
    this.expenseID = exepnseID;
    this.connection = connection;
  }

  /**
   * Gets the row value for the specified field of this group.
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

}
