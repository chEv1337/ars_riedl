import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@SessionScoped

public class WriteDatabase implements Serializable {

    public static boolean writePersonalData(String user, String password, String email, String companyName, String companyAddress, String companyPostal, String companyCity) {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement statement;
        int idCompany = 0;
        try {
            statement = con.prepareStatement("INSERT INTO company(companyname, companyaddress, companypostal, companycity)" +
                                            "VALUES(?, ?, ?, ?)");
            statement.setString(1, companyName);
            statement.setString(2, companyAddress);
            statement.setString(3, companyPostal);
            statement.setString(4, companyCity);
            statement.executeUpdate();
            statement = con.prepareStatement("SELECT idcompany FROM company WHERE companyname = ? AND companyaddress = ?");
            statement.setString(1, companyName);
            statement.setString(2, companyAddress);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                idCompany = rs.getInt("idcompany");
            }
            statement = con.prepareStatement("INSERT INTO user (username, password, email, admin, idcompany_fk)" +
                                            "VALUES(?, ?, ?, 0, ?)");
            statement.setString(1, user);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setInt(4, idCompany);
            statement.executeUpdate();
            con.close();
            return true;
        } catch (SQLException err) {
            System.out.println("ERROR @ WriteDatabase.writePersonalData --->" + err.getMessage());
            return false;
        }
    }
}
