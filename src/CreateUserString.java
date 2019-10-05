import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped

public class CreateUserString implements Serializable {

    public static String createUserString() {
        String userStringFinalized;
        HttpSession userSession = SessionData.getSession();
        String username = "User: " + userSession.getAttribute("user") + "\n";
        String email = "Email: " + userSession.getAttribute("email") + "\n";
        String companyName = "Firmenname: " + userSession.getAttribute("companyName") + "\n";
        String companyAddress = "Firmenadresse: " + userSession.getAttribute("companyAddress") + "\n";
        String companyPostal = "Stadt: " + userSession.getAttribute("companyPostal");
        String companyCity = " " + userSession.getAttribute("companyCity") + "\n";

        userStringFinalized = username + email + companyName + companyAddress + companyPostal + companyCity;

        return userStringFinalized;
    }
}
