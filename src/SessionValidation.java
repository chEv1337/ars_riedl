
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named
@SessionScoped
public class SessionValidation implements Serializable {

    public static String validateSession() {
        if(SessionData.getUserId() == null) {
            return "login";
        }
        return "";
    }
}
