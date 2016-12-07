import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by KevinLiang on 12/7/16.
 */
public class GroupInvite {

  private int groupID;
  private Account accountInvited;
  private Connection connection;

  public GroupInvite(int groupID, Account accountInvited, Connection connection) {
    this.groupID = groupID;
    this.accountInvited = accountInvited;
    this.connection = connection;
  }

}
