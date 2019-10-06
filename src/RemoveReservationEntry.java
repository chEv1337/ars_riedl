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

@Named
@SessionScoped
public class RemoveReservationEntry implements Serializable {
    private int inputQuantity;
    private int inputArticleId;

    public RemoveReservationEntry() {};

    public RemoveReservationEntry(int inputQuantity, int inputArticleId) {
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

    public void removeEntry() {
        HttpSession userSession = SessionData.getSession();
        ArrayList<ArticleMapping> cartList = (ArrayList<ArticleMapping>) userSession.getAttribute("cartList");
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmnt;
        int quantityDatabase = 0;
        int artId = 0;
        try {
            stmnt = con.prepareStatement("SELECT quantity, idarticle FROM article WHERE idarticle = ?");
            stmnt.setInt(1,inputArticleId);
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                quantityDatabase = rs.getInt("quantity");
                artId = rs.getInt("idarticle");
            }
            if(artId == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Bitte überprüfen Sie Ihre Eingabe",
                        "Please try again or contact your administrator."));
            } else {
                for (ArticleMapping article : cartList) {
                    if (article.getArticleId() == inputArticleId) {
                        if (article.getArticleQuantity() < inputQuantity) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Bitte überprüfen Sie Ihre Eingabe",
                                    "Please try again or contact your administrator."));
                            break;
                        } else {
                            stmnt = con.prepareStatement("UPDATE article SET quantity = ? WHERE idarticle = ?");
                            stmnt.setInt(1, inputQuantity + quantityDatabase);
                            stmnt.setInt(2, inputArticleId);
                            stmnt.executeUpdate();
                            userSession.setAttribute("cartList", cartList);
                            if (article.getArticleQuantity() == inputQuantity) {
                                cartList.remove(article);
                                break;
                            } else {
                                article.setArticleQuantity(article.getArticleQuantity() - inputQuantity);
                                break;
                            }
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Bitte überprüfen Sie Ihre Eingabe",
                                "Please try again or contact your administrator."));
                    }
                }

            }
        } catch (SQLException err) {
            System.out.println("Error @ RemoveReservationEntry.java -->" + err.getMessage());
        }

    }
}
