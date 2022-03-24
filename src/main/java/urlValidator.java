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
}
