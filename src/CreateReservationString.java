import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class CreateReservationString implements Serializable {

    public static String createReservationString() {
        StringBuilder str = new StringBuilder();
        String reservationStringFinalized;
        HttpSession userSession = SessionData.getSession();
        ArrayList<ArticleMapping> cartList = (ArrayList<ArticleMapping>) userSession.getAttribute("cartList");
        for (ArticleMapping article:cartList) {
            str.append(article.getArticleId() + "   |   " + article.getArticleName() + "   |   " + article.getArticleQuantity() + "   |   "
                    + article.getArticlePrice() + "   |   " + article.getArticleWeight() + "\n");
        }
        str.append("\nReservierungsnummer: " + userSession.getAttribute("reservationId").toString() + "\n");
        reservationStringFinalized = str.toString();
        return reservationStringFinalized;
    }
}
