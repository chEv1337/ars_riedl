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
        str.append("Reservierungsnummer: " + userSession.getAttribute("reservationId").toString() + "\n");
        for (ArticleMapping article:cartList) {
            str.append(article.getArticleId() + "   |   " + article.getArticleName() + "   |   " + article.getArticleQuantity() + "   |   "
                    + article.getArticlePrice() + "   |   " + article.getArticleWeight() + "\n");
        }
        reservationStringFinalized = str.toString();
        return reservationStringFinalized;
    }
}
