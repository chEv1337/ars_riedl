import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped

public class LoopCartListToDatabase implements Serializable {

    public static void loopCartList() {
        HttpSession userSession = SessionData.getSession();
        ArrayList<ArticleMapping> cartList = (ArrayList<ArticleMapping>) userSession.getAttribute("cartList");
        for (ArticleMapping article : cartList) {
            InsertReservationDataIntoDatabase.insertReservation(article.getArticleId(),article.getArticleQuantity());
        }
    }
}
