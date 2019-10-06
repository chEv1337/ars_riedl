import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
public class SessionData {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    public static String getUserName() {
        HttpSession session = getSession();
        return session.getAttribute("user").toString();
    }
    public static String getUserId() {
        HttpSession session = getSession();
        if(session != null) {
            return session.getAttribute("userid").toString();
        } else {
            return null;
        }
    }
}
