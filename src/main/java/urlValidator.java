import java.awt.*;
import java.net.URI;
import java.util.regex.*;

public class urlValidator {
    private String Link;
    private String regx;

    public urlValidator(){
        this.regx = "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";
    }

    public void setLink(String link){
        this.Link = link;
    }

    public boolean validateURL(){
        Pattern p = Pattern.compile(this.regx);
        Matcher m = p.matcher(this.Link);
        if(this.Link == null){
            return false;
        }
        return m.matches();
    }

    public boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
