import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
@SessionScoped
public class AdminArticleManipulation implements Serializable {

    private int articleId;
    private String articleName;
    private int articleQuantity;
    private float articlePrice;
    private float articleWeight;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getArticleQuantity() {
        return articleQuantity;
    }

    public void setArticleQuantity(int articleQuantity) {
        this.articleQuantity = articleQuantity;
    }

    public float getArticlePrice() {
        return articlePrice;
    }

    public void setArticlePrice(float articlePrice) {
        this.articlePrice = articlePrice;
    }

    public float getArticleWeight() {
        return articleWeight;
    }

    public void setArticleWeight(float articleWeight) {
        this.articleWeight = articleWeight;
    }

    public AdminArticleManipulation() {

    }

    public AdminArticleManipulation(int articleId, String articleName, int articleQuantity, float articlePrice, float articleWeight) {
        this.articleId=articleId;
        this.articleName=articleName;
        this.articleQuantity=articleQuantity;
        this.articlePrice=articlePrice;
        this.articleWeight=articleWeight;
    }

    public void addArticle() {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt;
        try {
            if(articleQuantity < 0 || articlePrice < 0 || articleWeight < 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Bitte überprüfen Sie Ihre Eingabe",
                        "Please try again or contact your administrator."));
            } else {
                stmt = con.prepareStatement("INSERT INTO article(articlename, quantity, price, weight)"
                        + " VALUES (?,?,?,?)");
                stmt.setString(1, articleName);
                stmt.setInt(2, articleQuantity);
                stmt.setFloat(3, articlePrice);
                stmt.setFloat(4, articleWeight);
                stmt.executeUpdate();
                DatabaseConnection.closeConnection(con);
            }
        } catch (SQLException err) {
            System.out.println("Error @ AdminArticleManipulation --> " + err.getMessage());
        }
    }
    public void manipulateArticle() {
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement stmt;
        int checkId = 0;
        try {
            stmt = con.prepareStatement("SELECT idarticle FROM article WHERE idarticle = ?");
            stmt.setInt(1,articleId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                checkId = rs.getInt("idarticle");
            }
            if(articleQuantity < 0 || articlePrice < 0 || articleWeight < 0 || checkId == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Bitte überprüfen Sie Ihre Eingabe",
                        "Please try again or contact your administrator."));
            } else {
                stmt = con.prepareStatement("UPDATE article SET articlename = ?, quantity = ?, price = ?, weight = ? WHERE idarticle = ?");
                stmt.setString(1, articleName);
                stmt.setInt(2, articleQuantity);
                stmt.setFloat(3, articlePrice);
                stmt.setFloat(4, articleWeight);
                stmt.setInt(5, articleId);
                stmt.executeUpdate();
                DatabaseConnection.closeConnection(con);
            }
        } catch (SQLException err) {
            System.out.println("Error @ AdminArticleManipulation --> " + err.getMessage());
        }
    }
}
