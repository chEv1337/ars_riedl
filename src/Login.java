import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;


@Named
@SessionScoped

public class Login implements Serializable {

    private String password;
    private String user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String verifyUser() {
        boolean validation = LoginVerify.verify(user, password);
        if(validation) {
            HttpSession userSession = SessionData.getSession();
            userSession.setAttribute("user", user);
            userSession.setAttribute("sessionValid", "true");
            SetPersonalData.setData(userSession);
            return "welcome";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Incorrect Username or Password",
                    "Please check input"));
            return "login";
        }
    }
    public String logoutUser() {
        HttpSession session =  SessionData.getSession();
        session.invalidate();
        return "logout";
    }

}
