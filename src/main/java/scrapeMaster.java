import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.util.List;

import java.io.IOException;

public class scrapeMaster {
    private String url;

    public scrapeMaster(String link){
        this.url = link;
    }

    public void setLink(String link){
        this.url = link;
    }

    public void getData(){
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        try {
            HtmlPage page = webClient.getPage(this.url);

            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();
//            recipesFile.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }
}
