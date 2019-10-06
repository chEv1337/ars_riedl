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

public class ReservationEntry implements Serializable {

    private int inputQuantity;
    private int inputArticleId;

    public ReservationEntry() {};

    public ReservationEntry(int inputQuantity, int inputArticleId) {
        this.inputArticleId=inputArticleId;
        this.inputQuantity=inputQuantity;
    }

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
        HttpSession userSession = SessionData.getSession();
        List<ArticleMapping> cartList = (ArrayList<ArticleMapping>)userSession.getAttribute("cartList");

        try{
            statement = con.prepareStatement("SELECT quantity FROM article WHERE idarticle = ?");
            statement.setInt(1, inputArticleId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                counter = rs.getInt("quantity");
            }
            counter -= inputQuantity;
            //System.out.println("counter after calculation: " + counter);
            statement = con.prepareStatement("UPDATE article SET quantity = ? WHERE idarticle = ?");
            statement.setInt(1, counter);
            statement.setInt(2, inputArticleId);
            statement.executeUpdate();

            InsertReservationDataIntoDatabase.insertReservation(inputArticleId,inputQuantity);

            int cartArrayCounter = (int) userSession.getAttribute("cartArrayCounter");
            cartArrayCounter++;
            cartList.add(CreateCartList.getCartList(inputArticleId,inputQuantity));
            userSession.setAttribute("cartList", cartList);
            userSession.setAttribute("cartArrayCounter", cartArrayCounter);
            userSession.setAttribute("inputArticleId", inputArticleId);
            userSession.setAttribute("inputQuantity", inputQuantity);
            System.out.println(cartList.size() + " cartListSize @ ReservationEntry.java");
            System.out.println(cartArrayCounter + " : cartArrayCounter @ ReservationEntry.java");
            DatabaseConnection.closeConnection(con);

        } catch (SQLException err) {
            System.out.println("ERROR @ ReservationEntry --> " + err.getMessage());
        }
    }

}
