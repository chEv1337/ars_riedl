import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ArticleCreateList implements Serializable {

    public List<ArticleMapping> getArticles() {

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement;
        List<ArticleMapping> articles = new ArrayList<>();
        try {

            statement = connection.prepareStatement("SELECT * FROM article");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ArticleMapping articleMap = new ArticleMapping();
                articleMap.setArticleId(rs.getInt("idarticle"));
                articleMap.setArticleName(rs.getString("articlename"));
                articleMap.setArticleQuantity(rs.getInt("quantity"));
                articleMap.setArticlePrice(rs.getFloat("price"));
                articleMap.setArticleWeight(rs.getFloat("weight"));

                articles.add(articleMap);
            }
            rs.close();
            connection.close();
            statement.close();

        } catch (SQLException err) {
            System.out.println("Error @ ArticleCreateList --> " + err.getMessage());
        }
        return articles;
    }
}
