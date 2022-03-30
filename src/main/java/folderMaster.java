 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.nio.file.*;

public class folderMaster {
    private String fullFolderPath;
    private String folderPathWithName;

    public folderMaster(){
        this.fullFolderPath = System.getProperty("user.dir");
        this.folderPathWithName = "";
    }

    /**
     * Creating a space in length of the string
     * @param title - String to generate space for.
     */
    public String stringSpaceMaker(String title){
        String space = "";
        for(int x=0; x <= title.length(); x++){
            space += "=";
        }
        return  space + "\n\n";
    }

    public void writeLogFile(String log) {
        try{
            FileWriter output = new FileWriter(folderPathWithName + "\\log.txt");
            output.write(log);
            output.close();
        } catch (IOException exception) {
            System.err.println("Failed to create a text file!" + exception.getMessage());
        }
    }

    public String getUserDir(){
        return fullFolderPath;
    }

    /**
     * Creates folder inside -Resourced- folder
     * @param folder_name <-
     */
    public void mkDirAt(String folder_name) {
        this.folderPathWithName = getUserDir() + "\\Resourced\\" + folder_name;
        Path way = Paths.get(folderPathWithName);
         try {
             Files.createDirectories(way);
         } catch (IOException exception) {
             System.err.println("Failed to create a dir!" + exception.getMessage());
         }
    }

    /**
     * Returns last part after last / of the given url
     * @param url full url
     * @return last part of url
     */
    public String urlNameProcessor(String url){
        return url.substring( url.lastIndexOf('/') + 1 );
    }

    private Path getPath(String string){
        return Paths.get(folderPathWithName + "\\" + string.substring(string.lastIndexOf('/')+1));
    }

    public boolean saveImage(String imageURL){
        Path full = getPath(imageURL);

        if(Files.exists(full)){
            System.err.println("File exist");
            return false;
        }
            try(InputStream in = new URL(imageURL).openStream()){
                //TODO: enable save.
                //                Files.copy(in, full);
            }catch(IOException exception){
                System.err.println("Cannot save an image!" + exception.getMessage());
                return false;
            }
            return true;

    }
}
