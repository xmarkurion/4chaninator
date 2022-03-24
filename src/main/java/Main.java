import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args){
//        MainGUI app = new MainGUI("4chan Images Scraper v0.1");

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try {
            HtmlPage page = webClient.getPage("https://www.webscrapingapi.com/java-web-scraping/");
            webClient.getCurrentWindow().getJobManager().removeAllJobs();

            String title = page.getTitleText();
            System.out.println("Page Title: " + title);

            webClient.close();
//            recipesFile.close();


        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }
}
