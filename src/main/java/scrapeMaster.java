import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.io.IOException;

public class scrapeMaster {
    private boolean status = false;
    private String url;
    private String urlTitle;
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<catalogLink> catalogLinksArray = new ArrayList<>();

    public scrapeMaster() {}

    public void emptyArrayList(){
        images.clear();
    }

    /**
     * Set url link of the site
     * @param link - full link to the thread.
     */
    public void setLink(String link) {
        this.url = link;
    }

    public String getLink() {
        return this.url;
    }

    /**
     * @return Title of the thread
     */
    public String getUrlTitle() {
        return urlTitle;
    }

    /**
     * @return Amount of images found by app.
     */
    public int imagesAmount() {
        return images.size();
    }

    /**
     * @return Array List of taken images.
     */
    public ArrayList<String> getArrayListOfImages() {
        return images;
    }

    public void clearArrayListOfIamges(){
        images.clear();
    }

    /**
     * @return Array list of object catalogLink that contains links to threads.
     */
    public ArrayList<catalogLink> getArrayListOfCatalogLinks(){ return catalogLinksArray; }

    /**
     * If status true page was fully loaded and data was collected
     * If status false page is still processing
     *
     * @return Status of the page scrapping
     */
    public boolean getStatus() {
        return this.status;
    }

    /**
     * Void method getting the source
     */
    public void getData() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try {
            HtmlPage page = webClient.getPage(this.url);
            webClient.getCurrentWindow().getJobManager().removeAllJobs();

            urlTitle = page.getTitleText();

            List<?> anchors = page.getByXPath("//a[@class='fileThumb']");
            for (Object anchor : anchors) {
                HtmlAnchor link = (HtmlAnchor) anchor;
                String recipeLink = "https:" + link.getHrefAttribute();

                images.add(recipeLink);
            }
            webClient.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
        this.status = true;
    }

    public void getCatalogLinks(String lllink){
        catalogLinksArray.clear();
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.getOptions().setCssEnabled(false);

        try {
            HtmlPage page = webClient.getPage(lllink);
            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            System.out.println(page.getPage());

            DomNode domNode = page.querySelector(".thread");

            ArrayList<String> threadLinks = new ArrayList<String>();
            List<HtmlAnchor> anchors = page.getAnchors();
            anchors.forEach(e->{
                String atr =  e.getHrefAttribute();

                //Getting https link
                if(atr.contains("thread")){
                    String betterAtr = "https:"+atr;
                    threadLinks.add(betterAtr);
                }
            });

            threadLinks.forEach(e->{
                System.out.println("\n\n");
                //Final thread link
                String threadLink = e;
                System.out.println(e);

                //Prepare class-ID from given link
                String classID = "meta-" + e.substring(e.lastIndexOf("/")+1);

                //Finding image reply count
                System.out.print("Image Reply Count: ");
                DomNode thID = page.getElementById(classID);
                String el = thID.asNormalizedText();
                String stringImageReplyCount = el.substring(el.lastIndexOf("/")+5, el.length()-1);
                int imageReplyCount = (stringImageReplyCount.length() == 0)? 0 : Integer.parseInt(stringImageReplyCount);
                System.out.println(""+imageReplyCount);

                //Finding Title of the thread
                System.out.print("Title element: ");
                DomNode elTitle = thID.getNextElementSibling();
                String stringElTitle = "";
                if(elTitle != null){
                    stringElTitle = elTitle.asNormalizedText();
                }else
                    stringElTitle = "Empty";

                System.out.println(stringElTitle);

                if(imageReplyCount > 0){
                    catalogLinksArray.add(new catalogLink(threadLink,stringElTitle,imageReplyCount));
                }
            });
            catalogLinksArray.sort(Comparator.comparing(catalogLink::getImagesReplyCount).reversed());

            webClient.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }



}
