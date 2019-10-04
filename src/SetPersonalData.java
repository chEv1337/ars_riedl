import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Named
@SessionScoped

public class SetPersonalData implements Serializable {

    public static void setData(HttpSession userSession) {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT email, companyname, companyaddress, companypostal, companycity " +
                                                        "FROM mydb.user LEFT JOIN company ON user.idcompany_fk = company.idcompany " +
                                                        "WHERE username = ? ");
            statement.setString(1, userSession.getAttribute("user").toString());
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
               String email = rs.getString("email");
               userSession.setAttribute("email", email);
               String companyName = rs.getString("companyname");
               userSession.setAttribute("companyName", companyName);
               String companyAddress = rs.getString("companyaddress");
               userSession.setAttribute("companyAddress", companyAddress);
               String companyPostal = rs.getString("companypostal");
               userSession.setAttribute("companyPostal", companyPostal);
               String companyCity = rs.getString("companycity");
               userSession.setAttribute("companyCity", companyCity);
            }
            userSession.setAttribute("cartArrayCounter", 0);
            List<ArticleMapping> cartList = new ArrayList<>();
            userSession.setAttribute("cartList", cartList);
            connection.close();
        } catch (SQLException err) {
            System.out.println("ERROR@SetPersonalDat -->>" + err.getMessage());
        }
    }


}
