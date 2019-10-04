import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Named
@SessionScoped
public class CreateReservationFile implements Serializable {

    public void createReservationFile() throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date date = new Date();
        HttpSession userSession = SessionData.getSession();
        String location = "C:/project/export_" + userSession.getAttribute("user") + formatter.format(date) + ".txt";

        Writer exportWriter = new FileWriter(location);
        try {
            ArrayList<ArticleMapping> cartList = (ArrayList<ArticleMapping>) userSession.getAttribute("cartList");
            exportWriter.write("EINE NEUE RESERVIERUNG IST EINGEGANGEN!\n");
            for(ArticleMapping article:cartList) {
                exportWriter.write(article.getArticleId() + " " + article.getArticleName() + " " + article.getArticleQuantity() + " "
                + article.getArticlePrice() + " " + article.getArticleWeight() + "\n");
            }
            exportWriter.write("BESTELLER:\n");
            exportWriter.write("Username: " + userSession.getAttribute("user") + "\n");
            exportWriter.write("Email: " + userSession.getAttribute("email") + "\n");
            exportWriter.write("companyName: " + userSession.getAttribute("companyName") + "\n");
            exportWriter.write("companyAddress: " + userSession.getAttribute("companyAddress") + "\n");
            exportWriter.write("companyPostal: " + userSession.getAttribute("companyPostal") + "\n");
            exportWriter.write("companyCity: " + userSession.getAttribute("companyCity") + "\n");
        } catch (IOException err) {
            System.out.println("Error @ CreateReservationFile.java -->" + err.getMessage());
        } finally {
            exportWriter.close();
        }
    }
}
