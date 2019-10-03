import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;

@Named
@SessionScoped
public class createReservationTable implements Serializable {

    public void getReservationData() {
        HttpSession userSession = SessionData.getSession();
        /*ArrayList<ArticleMapping> tst = new ArrayList<>();
        tst = (ArrayList<ArticleMapping>)userSession.getAttribute("cartList");
        int counter = (int)userSession.getAttribute("cartArrayCounter");
        counter -= 1;
        System.out.println(tst.get(counter) + ": Tst");*/
    }
}
