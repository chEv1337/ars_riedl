import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@SessionScoped
public class CreateReservationFile implements Serializable {

    public String createReservationFile() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date date = new Date();
        HttpSession userSession = SessionData.getSession();
        LoopCartListToDatabase.loopCartList();
        String location = "C:/project/export_" + userSession.getAttribute("user") + formatter.format(date) + ".txt";
        userSession.setAttribute("location", location);
        try (Writer exportWriter = new FileWriter(location)) {
            exportWriter.write("EINE NEUE RESERVIERUNG IST EINGEGANGEN!\n\n");
            exportWriter.write("ArtikelID   |   Artikelname |   Anzahl  |   Preis   |   Gewicht  \n\n");
            exportWriter.write(CreateReservationString.createReservationString());
            exportWriter.write("\nBESTELLER:\n\n");
            exportWriter.write(CreateUserString.createUserString());
        } catch (IOException err) {
            System.out.println("Error @ CreateReservationFile.java -->" + err.getMessage());
        }

        DeleteCart.deleteUserCart();
        return "success";
    }
}
