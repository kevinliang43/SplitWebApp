import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by KevinLiang on 12/3/16.
 */
public class ApplicationController {

  public static void main(String[] args) {

    ApplicationModel model = new ApplicationModel();
    Scanner scanner = new Scanner(System.in);

    // Step 1: Attempts to get a connection

    while (model.getConnection() == null) {
      String username;
      String password;

      System.out.println("Please enter a username: ");
      username = scanner.nextLine();
      System.out.println("Please enter a password: ");
      password = scanner.nextLine();

      model.getConnection(username, password, "GroupExpensesApplication");


    }

    if (model.getConnection() != null) {
      System.out.println("Connection successful");
    }

    try {
      model.accountLogin("kevinliang1", "123");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    System.out.println(model.getLogin().checkField("firstName"));

//    System.out.println(model.getLogin().getField("firstName"));
//    System.out.println(model.getLogin().getField("lastName"));
//    System.out.println(model.getLogin().getField("accountID"));
//    System.out.println(model.getLogin().getField("username"));
//    System.out.println(model.getLogin().getField("password"));



  }



}
