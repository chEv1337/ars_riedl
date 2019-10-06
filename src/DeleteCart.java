import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped

public class DeleteCart implements Serializable {

    public static void deleteUserCart() {
        HttpSession userSession = SessionData.getSession();
        userSession.removeAttribute("cartList");
        List<ArticleMapping> newCartList = new ArrayList<>();
        userSession.setAttribute("cartList", newCartList);
        userSession.setAttribute("cartArrayCounter", 0);
        int reserID = 1 + (int)userSession.getAttribute("reservationId");
        userSession.setAttribute("reservationId", reserID);
    }
}
