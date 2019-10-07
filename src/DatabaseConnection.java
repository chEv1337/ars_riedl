import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;

@Named
public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb?user=root");
            return con;
        } catch (Exception err) {
            System.out.println("DatabaseConnection.getConnection() Error -->"
                    + err.getMessage());
            return null;
        }
    }
    public static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (Exception err) {
            System.out.println(err);
        }
    }
}
