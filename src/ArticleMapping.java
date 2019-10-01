import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ArticleMapping implements Serializable {

    private int articleId;
    private String articleName;
    private int articleQuantity;
    private float articlePrice;
    private float articleWeight;

    public ArticleMapping() {

    }

    public ArticleMapping(Integer articleId, String articleName, int articleQuantity, float articlePrice, float articleWeight) {
        this.articleId=articleId;
        this.articleName=articleName;
        this.articleQuantity=articleQuantity;
        this.articlePrice=articlePrice;
        this.articleWeight=articleWeight;
    }

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
}
