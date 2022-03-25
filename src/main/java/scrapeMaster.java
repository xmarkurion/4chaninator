import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class scrapeMaster {
    private String url;
    private String urlTitle;
    private ArrayList<String> images = new ArrayList<>();

    public scrapeMaster(){
//        this.url = "https://boards.4chan.org/b/thread/875043412/so-i-got-sent-a-video-of-my-girlfriend-making-out";
//        getData();
    }

    /**
     * Set url link of the site
     * @param link - full link to the thread.
     */
    public void setLink(String link){
        this.url = link;
    }

    /**
     * @return Title of the thread
     */
    public String getUrlTitle(){return urlTitle;}

    /**
     * @return Amount of images found by app.
     */
    public int imagesAmount(){return images.size();}

    /**
     * @return Array List of taken images.
     */
    public ArrayList<String> getArrayListOfImages(){return images;}

    /**
     * Void method getting the source
     */
    public void getData(){
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        try {
            HtmlPage page = webClient.getPage(this.url);
            webClient.getCurrentWindow().getJobManager().removeAllJobs();

            urlTitle = page.getTitleText();

            List<?> anchors = page.getByXPath("//a[@class='fileThumb']");
            for (Object anchor : anchors) {
                HtmlAnchor link = (HtmlAnchor) anchor;
                String recipeLink = "https:"+link.getHrefAttribute();

                images.add(recipeLink);
//              System.out.println(recipeLink);
            }

            webClient.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }


}