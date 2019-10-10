import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
        int id=0;
        HttpSession userSession = SessionData.getSession();
        List<ArticleMapping> cartList = (ArrayList<ArticleMapping>)userSession.getAttribute("cartList");

        try{
            statement = con.prepareStatement("SELECT quantity, idarticle FROM article WHERE idarticle = ?");
            statement.setInt(1, inputArticleId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                counter = rs.getInt("quantity");
                id = rs.getInt("idarticle");
            }
            counter -= inputQuantity;
            if(counter < 0 || id == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Bitte überprüfen Sie Ihre Eingabe",
                        "Please try again or contact your administrator."));
            } else {
                statement = con.prepareStatement("UPDATE article SET quantity = ? WHERE idarticle = ?");
                statement.setInt(1, counter);
                statement.setInt(2, inputArticleId);
                statement.executeUpdate();

                int cartArrayCounter = (int) userSession.getAttribute("cartArrayCounter");
                cartArrayCounter++;
                cartList.add(CreateCartList.getCartList(inputArticleId, inputQuantity));
                userSession.setAttribute("cartList", cartList);
                userSession.setAttribute("cartArrayCounter", cartArrayCounter);
                userSession.setAttribute("inputArticleId", inputArticleId);
                userSession.setAttribute("inputQuantity", inputQuantity);
            }
            DatabaseConnection.closeConnection(con);
        } catch (SQLException err) {
            System.out.println("ERROR @ ReservationEntry --> " + err.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Eingabe war nicht korrekt. ",
                    "Please try again or contact your administrator."));
        }
    }

}
