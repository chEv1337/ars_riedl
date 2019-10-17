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
    public static void setUserName(String newUserName) {
        HttpSession session = getSession();
        session.setAttribute("user", newUserName);
    }
    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            if (session.getAttribute("userid") != null) {
                return session.getAttribute("userid").toString();
            }
        }
        return null;
    }
}
