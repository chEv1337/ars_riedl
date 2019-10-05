import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.sql.*;

@Named

public class LoginVerify {

    public static boolean verify(String user, String password) {
        Connection connection = null;
        PreparedStatement statement;
        HttpSession userSession = SessionData.getSession();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement("SELECT username, password, iduser FROM mydb.user WHERE username = ? AND password = ?;");
            statement.setString(1, user);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            if(set.next()) {
                userSession.setAttribute("userid", set.getInt("iduser"));
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
