 import java.io.IOException;
 import java.nio.file.*;

public class folderMaster {
    private String fullFolderPath;

    public folderMaster(){
        this.fullFolderPath = System.getProperty("user.dir");
    }

    public String getUserDir(){
        return fullFolderPath;
    }

    /**
     * Creates folder inside -Resourced- folder
     * @param folder_name <-
     */
    public void mkDirAt(String folder_name) {
        Path way = Paths.get(getUserDir() + "\\Resourced\\" + folder_name);
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
}


// package com.Markurion;

// import java.io.IOException;
// import java.nio.file.*;

// public class Main {

//     public static String workDir(){
//         return System.getProperty("user.dir");
//     }

//     public static void mkDirAt(Path way){
//         try{
//             Files.createDirectories(way);
//         }catch (IOException exception){
//             System.err.println("Failed to create a dir!"+ exception.getMessage());
//         }

//     }

//     public static void main(String[] args) {
//         System.out.println(workDir());
//         mkDirAt(Paths.get(workDir()));
//     }
// }