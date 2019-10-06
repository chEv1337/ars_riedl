import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class Register implements Serializable {

    private String user;
    private String password;
    private String email;
    private String companyName;
    private String companyAddress;
    private String companyPostal;
    private String companyCity;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public void setCompanyPostal(String companyPostal) {
        this.companyPostal = companyPostal;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getCompanyPostal() {
        return companyPostal;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public String registerUser() {

        boolean validator = InsertPersonalDataInDatabase.writePersonalData(user, password, email, companyName, companyAddress, companyPostal, companyCity);
        if(validator) {
            HttpSession session = SessionData.getSession();
            session.setAttribute("user", user);
            SetPersonalData.setData(session);
            return "welcome";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Something went wrong while you registered. ",
                    "Please try again or contact your administrator."));
            return "register";
        }
    }
}
