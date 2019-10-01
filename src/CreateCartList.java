import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Named
@SessionScoped

public class CreateCartList implements Serializable {

    public static ArrayList<ArticleMapping> getCartList(int articleId, int inputQuantity) {
        ArticleMapping cartEntry = new ArticleMapping();
        ArrayList<ArticleMapping> cartList = new ArrayList<>();
        cartEntry.setArticleId(articleId);
        cartEntry.setArticleQuantity(inputQuantity);
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement statement;
        try {
            statement = con.prepareStatement("SELECT * FROM article WHERE idarticle = ?");
            statement.setInt(1, articleId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                cartEntry.setArticleName(rs.getString("articlename"));
                cartEntry.setArticleWeight(rs.getFloat("weight") * (float) inputQuantity);
                cartEntry.setArticlePrice(rs.getFloat("price") * (float) inputQuantity);
            }
            cartList.add(cartEntry);
            DatabaseConnection.closeConnection(con);
        } catch (SQLException err) {
            System.out.println("ERROR @ CreateCartList --> " + err.getMessage());
        }
        //test
        //test2
        return cartList;
    }
}
