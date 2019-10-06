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
public class FetchReservations implements Serializable {
    private String username;
    private int reservationNumber;
    private int idArticle;
    private String articleName;
    private int reservationQuantity;

    public FetchReservations() {}

    public FetchReservations(String username, int reservationNumber, int idArticle, String articleName, int reservationQuantity) {
        this.username = username;
        this.reservationNumber = reservationNumber;
        this.idArticle = idArticle;
        this.articleName = articleName;
        this.reservationQuantity = reservationQuantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getReservationQuantity() {
        return reservationQuantity;
    }

    public void setReservationQuantity(int reservationQuantity) {
        this.reservationQuantity = reservationQuantity;
    }

    public List<FetchReservations> getReservations() {
        List<FetchReservations> reservations = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement;
        HttpSession userSession = SessionData.getSession();

        try {

            statement = connection.prepareStatement("SELECT username, reservationnumber, idarticle, articlename, reservationquantity FROM reservation " +
                    "left join user on reservation.iduser_fk = user.iduser " +
                    "left join article on reservation.idarticle_fk = article.idarticle WHERE iduser = ?;");
            statement.setInt(1,(int)userSession.getAttribute("userid"));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FetchReservations fetch = new FetchReservations();
                fetch.setUsername(rs.getString("username"));
                fetch.setReservationNumber(rs.getInt("reservationnumber"));
                fetch.setIdArticle(rs.getInt("idarticle"));
                fetch.setArticleName(rs.getString("articlename"));
                fetch.setReservationQuantity(rs.getInt("reservationquantity"));

                reservations.add(fetch);
            }
            rs.close();
            connection.close();
            statement.close();

        } catch (SQLException err) {
            System.out.println("Error @ ArticleCreateList --> " + err.getMessage());
        }

        return reservations;
    }
}
