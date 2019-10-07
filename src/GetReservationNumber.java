import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@SessionScoped
public class GetReservationNumber implements Serializable {

    public static void getNumber() {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmnt;
        HttpSession userSession = SessionData.getSession();
        int reservationId = 0;
        try {
            stmnt = con.prepareStatement("SELECT max(idreservation), max(reservationnumber) FROM reservation");
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                reservationId = 1 + rs.getInt("max(reservationnumber)");
            }
            userSession.setAttribute("reservationId",reservationId);
        } catch (SQLException err) {
            System.out.println("Error @ GetReservationNumber.java --> " + err.getMessage());
        }

    }
}
