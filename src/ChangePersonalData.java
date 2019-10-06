import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@SessionScoped
public class ChangePersonalData implements Serializable {

    private String userName;
    private String password;
    private String email;
    private String companyName;
    private String companyAddress;
    private String companyPostal;
    private String companyCity;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPostal() {
        return companyPostal;
    }

    public void setCompanyPostal(String companyPostal) {
        this.companyPostal = companyPostal;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public void change(String input, String column) {
        String user;
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmnt;
        user = SessionData.getUserName();
        String sql = null;
        switch (column) {
            case "companyname":
                sql = "UPDATE company SET companyname = ? WHERE idcompany = ?";
                break;
            case "companyaddress":
                sql = "UPDATE company SET companyaddress = ? WHERE idcompany = ?";
                break;
            case "companypostal":
                sql = "UPDATE company SET companypostal = ? WHERE idcompany = ?";
                break;
            case "companycity":
                sql = "UPDATE company SET companycity = ? WHERE idcompany = ?";
                break;
            case "username":
                sql = "UPDATE user SET username = ? WHERE username = ?";
                break;
            case "password":
                sql = "UPDATE user SET password = ? WHERE username = ?";
                break;
            case "email":
                sql = "UPDATE user SET email = ? WHERE username = ?";
                break;
        }
        if(column.equals("companyname") || column.equals("companyaddress") || column.equals("companypostal") || column.equals("companycity") ) {
            try {
                int compFk = 0;
                stmnt = con.prepareStatement("SELECT idcompany_fk FROM user WHERE username = ?");
                stmnt.setString(1,user);
                ResultSet rs = stmnt.executeQuery();
                while (rs.next()) {
                    compFk = rs.getInt("idcompany_fk");
                }
                stmnt = con.prepareStatement(sql);
                stmnt.setString(1,input);
                stmnt.setInt(2,compFk);
                stmnt.executeUpdate();
            } catch (SQLException err) {
                System.out.println("Error @ ChangePersonalData.java / company if clause --> " + err.getMessage());
            }
        } else {
            try {
                stmnt = con.prepareStatement(sql);
                stmnt.setString(1, input);
                stmnt.setString(2, user);
                stmnt.executeUpdate();
                if(column.equals("username")) {
                    SessionData.setUserName(input);
                }
            } catch (SQLException err) {
                System.out.println("Error @ ChangePersonalData.java / user if clause --> " + err.getMessage());
            }
        }
        SetPersonalData.setData(SessionData.getSession());
    }
}
