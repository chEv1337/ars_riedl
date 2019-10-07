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
public class InsertReservationDataIntoDatabase implements Serializable {

    public static void insertReservation(int id, int quant) {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmnt;
        int reservationId = 0;
        HttpSession userSession = SessionData.getSession();
        try {
            reservationId = (int)userSession.getAttribute("reservationId");
            stmnt = con.prepareStatement("INSERT INTO reservation(reservationnumber, idarticle_fk, iduser_fk, reservationquantity)" +
                    "VALUES(?, ?, ?, ?)");
            stmnt.setInt(1, reservationId);
            stmnt.setInt(2,id);
            stmnt.setInt(3,(int) userSession.getAttribute("userid"));
            stmnt.setInt(4,quant);
            stmnt.executeUpdate();
        } catch (SQLException err) {
            System.out.println("Error @ InsertReservationDataIntoDatabase.java -->" + err.getMessage());
        }
    }
}
