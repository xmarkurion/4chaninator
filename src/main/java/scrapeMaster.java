import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class scrapeMaster {
    private boolean status = false;
    private String url;
    private String urlTitle;
    private ArrayList<String> images = new ArrayList<>();

    public scrapeMaster() {
//        this.url = "https://boards.4chan.org/b/thread/875043412/so-i-got-sent-a-video-of-my-girlfriend-making-out";
//        getData();
    }

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

    public void getCatalogLinks(){
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.getOptions().setCssEnabled(false);

        try {
            String web = "https://boards.4chan.org/wg/catalog";
            HtmlPage page = webClient.getPage(web);
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
                System.out.println(elTitle.asNormalizedText());
            });

//            DomNodeList<?> internal = domNode.getChildNodes();
//            internal.forEach(e->{
//                System.out.println(e.getByXPath("//@href"));
//                    //System.out.println(e.getPrefix());
//                    System.out.println(e.asNormalizedText());
//
//                });




            //Multi element
//            DomNodeList<?> domNodeList = page.querySelectorAll(".thread");
//            domNodeList.forEach(node -> {
//                System.out.println(node.asNormalizedText());
//            });

//            HtmlElement head = page.getHead();


//            List<?> anchors = page.getByXPath("\"//div[@class='thread']\"");
//            for (Object anchor : anchors) {
//                HtmlAnchor link = (HtmlAnchor) anchor;
//                HtmlPage hpage = (HtmlPage) anchor;
//                String dat = hpage.asNormalizedText();
//                System.out.println(dat);
////                String recipeLink = "https:" + link.getHrefAttribute();
////                images.add(recipeLink);
//            }
            webClient.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }


}
