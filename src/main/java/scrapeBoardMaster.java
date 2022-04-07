import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class scrapeBoardMaster {
    private String url;
    private ArrayList<String> safe = new ArrayList<>();
    private ArrayList<String> unSafe = new ArrayList<>();

    public scrapeBoardMaster(){
        this.url = "https://www.4chan.org/";
    }

    private void getBoardLinks(){
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try {
            HtmlPage page = webClient.getPage(this.url);
            webClient.getCurrentWindow().getJobManager().removeAllJobs();

            List<?> anchors = page.getByXPath("//a[@class='boardlink']");
            for (Object anchor : anchors) {
                HtmlAnchor link = (HtmlAnchor) anchor;

                String recipeLink = "https:" + link.getHrefAttribute();

                String data = recipeLink + " ," + link.getTextContent();
                if(!link.getHrefAttribute().contains("thread")){
                    if(link.getHrefAttribute().contains("4channel")){
                        safe.add(data);
                    }else {
                        unSafe.add(data);
                    }
                }
            }
            webClient.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }

    private void printSafe(){
        safe.forEach(System.out::println);
    }

    private void printUnSafe(){
        unSafe.forEach(System.out::println);
    }

    public String[] getSafeLinkArray(){
        String[] safeLink = {
                "https://boards.4channel.org/a/" ,
                "https://boards.4channel.org/c/" ,
                "https://boards.4channel.org/w/" ,
                "https://boards.4channel.org/m/" ,
                "https://boards.4channel.org/cgl/",
                "https://boards.4channel.org/cm/" ,
                "https://boards.4channel.org/n/" ,
                "https://boards.4channel.org/jp/" ,
                "https://boards.4channel.org/vt/" ,
                "https://boards.4channel.org/v/" ,
                "https://boards.4channel.org/vg/" ,
                "https://boards.4channel.org/vm/" ,
                "https://boards.4channel.org/vmg/" ,
                "https://boards.4channel.org/vp/" ,
                "https://boards.4channel.org/vr/" ,
                "https://boards.4channel.org/vrpg/" ,
                "https://boards.4channel.org/vst/" ,
                "https://boards.4channel.org/co/" ,
                "https://boards.4channel.org/g/" ,
                "https://boards.4channel.org/tv/" ,
                "https://boards.4channel.org/k/" ,
                "https://boards.4channel.org/o/" ,
                "https://boards.4channel.org/an/" ,
                "https://boards.4channel.org/tg/" ,
                "https://boards.4channel.org/sp/" ,
                "https://boards.4channel.org/xs/" ,
                "https://boards.4channel.org/pw/" ,
                "https://boards.4channel.org/sci/" ,
                "https://boards.4channel.org/his/" ,
                "https://boards.4channel.org/int/" ,
                "https://boards.4channel.org/out/" ,
                "https://boards.4channel.org/toy/" ,
                "https://boards.4channel.org/po/" ,
                "https://boards.4channel.org/p/" ,
                "https://boards.4channel.org/ck/" ,
                "https://boards.4channel.org/lit/" ,
                "https://boards.4channel.org/mu/" ,
                "https://boards.4channel.org/fa/" ,
                "https://boards.4channel.org/3/" ,
                "https://boards.4channel.org/gd/" ,
                "https://boards.4channel.org/diy/" ,
                "https://boards.4channel.org/wsg/" ,
                "https://boards.4channel.org/qst/" ,
                "https://boards.4channel.org/biz/" ,
                "https://boards.4channel.org/trv/" ,
                "https://boards.4channel.org/fit/" ,
                "https://boards.4channel.org/x/" ,
                "https://boards.4channel.org/adv/" ,
                "https://boards.4channel.org/lgbt/" ,
                "https://boards.4channel.org/mlp/" ,
                "https://boards.4channel.org/news/" ,
                "https://boards.4channel.org/wsr/" ,
                "https://boards.4channel.org/vip/"
        };
        return safeLink;
    }
    public String[] getSafeLinkTitle(){
        String[] safeLinkTitle = {
                "Anime & Manga" ,
                "Anime/Cute" ,
                "Anime/Wallpapers" ,
                "Mecha" ,
                "Cosplay & EGL" ,
                "Cute/Male" ,
                "Transportation" ,
                "Otaku Culture" ,
                "Virtual YouTubers" ,
                "Video Games" ,
                "Video Game Generals" ,
                "Video Games/Multiplayer" ,
                "Video Games/Mobile" ,
                "Pokemon" ,
                "Retro Games" ,
                "Video Games/RPG" ,
                "Video Games/Strategy" ,
                "Comics & Cartoons" ,
                "Technology" ,
                "Television & Film" ,
                "Weapons" ,
                "Auto" ,
                "Animals & Nature" ,
                "Traditional Games" ,
                "Sports" ,
                "Extreme Sports" ,
                "Professional Wrestling" ,
                "Science & Math" ,
                "History & Humanities" ,
                "International" ,
                "Outdoors" ,
                "Toys" ,
                "Papercraft & Origami" ,
                "Photography" ,
                "Food & Cooking" ,
                "Literature" ,
                "Music" ,
                "Fashion" ,
                "3DCG" ,
                "Graphic Design" ,
                "Do-It-Yourself" ,
                "Worksafe GIF" ,
                "Quests" ,
                "Business & Finance" ,
                "Travel" ,
                "Fitness" ,
                "Paranormal" ,
                "Advice" ,
                "LGBT" ,
                "Pony" ,
                "Current News" ,
                "Worksafe Requests" ,
                "Very Important Posts"
        };
        return safeLinkTitle;
    }

    public String[] getUnSafeLinkArray(){
        String[] unsafe = {
                "https://boards.4chan.org/f/" ,
                "https://boards.4chan.org/i/" ,
                "https://boards.4chan.org/ic/" ,
                "https://boards.4chan.org/wg/" ,
                "https://boards.4chan.org/b/" ,
                "https://boards.4chan.org/r9k/" ,
                "https://boards.4chan.org/pol/" ,
                "https://boards.4chan.org/bant/" ,
                "https://boards.4chan.org/soc/" ,
                "https://boards.4chan.org/s4s/" ,
                "https://boards.4chan.org/s/" ,
                "https://boards.4chan.org/hc/" ,
                "https://boards.4chan.org/hm/" ,
                "https://boards.4chan.org/h/" ,
                "https://boards.4chan.org/e/" ,
                "https://boards.4chan.org/u/" ,
                "https://boards.4chan.org/d/" ,
                "https://boards.4chan.org/y/" ,
                "https://boards.4chan.org/t/" ,
                "https://boards.4chan.org/hr/" ,
                "https://boards.4chan.org/gif/" ,
                "https://boards.4chan.org/aco/" ,
                "https://boards.4chan.org/r/"
        };
        return unsafe;
    };
    public String[] getUnSafeLinkTitle(){
        String[] unSafeLinkTitle = {
                "Flash" ,
                "Oekaki" ,
                "Artwork/Critique" ,
                "Wallpapers/General" ,
                "Random" ,
                "ROBOT9001" ,
                "Politically Incorrect" ,
                "International/Random" ,
                "Cams & Meetups" ,
                "Shit 4chan Says" ,
                "Sexy Beautiful Women" ,
                "Hardcore" ,
                "Handsome Men" ,
                "Hentai" ,
                "Ecchi" ,
                "Yuri" ,
                "Hentai/Alternative" ,
                "Yaoi" ,
                "Torrents" ,
                "High Resolution" ,
                "Adult GIF" ,
                "Adult Cartoons" ,
                "Adult Requests"
        };
        return unSafeLinkTitle;
    };

    public String[] getAllLinkArray(){
       return ArrayUtils.addAll(getSafeLinkArray(),getUnSafeLinkArray());
    }
    public String[] getAllLinkTitle(){
        return ArrayUtils.addAll(getSafeLinkTitle(),getUnSafeLinkTitle());
    }

}


