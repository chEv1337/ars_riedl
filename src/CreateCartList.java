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

public class CreateCartList implements Serializable {

    public List<ArticleMapping> getCartList(int articleId, int inputQuantity) {

        List<ArticleMapping> cartList = new ArrayList<>();

        Connection con = DatabaseConnection.getConnection();
        PreparedStatement statement;
        HttpSession userSession = SessionData.getSession();
        //System.out.println(articleId + " : articleID; " + inputQuantity + " : inputquantity");
        try {
            statement = con.prepareStatement("SELECT * FROM article WHERE idarticle = ?");
            statement.setInt(1, articleId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                ArticleMapping cartEntry = new ArticleMapping();
                cartEntry.setArticleId(articleId);
                cartEntry.setArticleQuantity(inputQuantity);
                cartEntry.setArticleName(rs.getString("articlename"));
                cartEntry.setArticleWeight(rs.getFloat("weight") * (float) inputQuantity);
                cartEntry.setArticlePrice(rs.getFloat("price") * (float) inputQuantity);
                cartList.add(cartEntry);
            }
            System.out.println(cartList.size() + " cartListSize @ CreateCartList.java");
            userSession.setAttribute("cartList", cartList);
            DatabaseConnection.closeConnection(con);

        } catch (SQLException err) {
            System.out.println("ERROR @ CreateCartList --> " + err.getMessage());
        }
        return cartList;
    }
}
