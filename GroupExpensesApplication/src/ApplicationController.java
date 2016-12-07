import java.sql.SQLException;
import java.util.ArrayList;
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

//      System.out.println("Please enter a username: ");
//      username = scanner.nextLine();
//      System.out.println("Please enter a password: ");
//      password = scanner.nextLine();
//
//      model.getConnection(username, password, "GroupExpensesApplication");

      model.getConnection("root", "kevin123", "GroupExpensesApplication");


    }

    if (model.getConnection() != null) {
      System.out.println("Connection successful");
    }

    try {
      model.accountLogin("kevinliang1", "123");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }


//    ArrayList<Account> noobs = new ArrayList<Account>();
//    noobs.add(new Account(4, model.getConnection()));
//    noobs.add(new Account(5, model.getConnection()));
//    noobs.add(new Account(6, model.getConnection()));
//
//   model.getLogin().addExpense(new Group(1, model.getConnection()),
//           10, noobs, 6, false, "2016-12-23", "Lunch");

    System.out.println(model.generateExpenseID());
  }



}
