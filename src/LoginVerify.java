import javax.inject.Named;
import java.sql.*;

@Named

public class LoginVerify {

    public static boolean verify(String user, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement("SELECT username, password FROM mydb.user WHERE username = ? AND password = ?;");
            statement.setString(1, user);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            if(set.next()) {
                /*String response = set.getString("username");
                System.out.println(response + "\n");*/
                return true;
            }
        } catch (SQLException err){
            System.out.println("Login error -->" + err.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

}
