import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Named
@SessionScoped
public class ReservationEntry implements Serializable {

    private int inputQuantity;
    private int inputArticleId;

    public int getInputQuantity() {
        return inputQuantity;
    }

    public void setInputQuantity(int inputQuantity) {
        this.inputQuantity = inputQuantity;
    }

    public int getInputArticleId() {
        return inputArticleId;
    }

    public void setInputArticleId(int inputArticleId) {
        this.inputArticleId = inputArticleId;
    }

    public void createReservationEntries() {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement statement;
        int counter = 0;

        try{
            statement = con.prepareStatement("SELECT quantity FROM article WHERE idarticle = ?");
            statement.setInt(1, inputArticleId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                counter = rs.getInt("quantity");
                System.out.println("Quantity from DB: " + rs.getString("quantity"));
            }
            counter -= inputQuantity;
            System.out.println("counter after calculation: " + counter);
            statement = con.prepareStatement("UPDATE article SET quantity = ? WHERE idarticle = ?");
            statement.setInt(1, counter);
            statement.setInt(2, inputArticleId);
            statement.executeUpdate();
            statement = con.prepareStatement("SELECT quantity FROM article WHERE idarticle = ?");
            statement.setInt(1, inputArticleId);
             rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("Quantity from DB after update: " + rs.getString("quantity"));
            }

            ArrayList<ArticleMapping> cartList;
            cartList = CreateCartList.getCartList(inputArticleId, inputQuantity);

            HttpSession userSession = SessionData.getSession();
            userSession.setAttribute("cartList", cartList);
            DatabaseConnection.closeConnection(con);
            for(ArticleMapping cartEntry : cartList) {
                System.out.println(cartEntry);
            }
        } catch (SQLException err) {
            System.out.println("ERROR @ ReservationEntry --> " + err.getMessage());
        }
    }

}
