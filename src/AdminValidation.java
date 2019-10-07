import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@SessionScoped
public class AdminValidation implements Serializable {
    public String validateAdmin() {
        if (SessionValidation.validateSession().equals("login")) {
            return "welcome";
        } else {
            String username =  SessionData.getUserName();
            Connection con = DatabaseConnection.getConnection();
            int isAdmin = 0;
            PreparedStatement stmnt;
            try{
                stmnt = con.prepareStatement("SELECT admin FROM user WHERE username = ?");
                stmnt.setString(1,username);
                ResultSet rs = stmnt.executeQuery();
                while (rs.next()) {
                    isAdmin = rs.getInt("admin");
                    if (isAdmin == 0) {
                        return "welcome";
                    }
                }
                DatabaseConnection.closeConnection(con);
            } catch (SQLException err) {
                System.out.println("Error @ AdminValidation.java --> " + err.getMessage());
            }
            return "articleAdmin";
        }
    }
}
