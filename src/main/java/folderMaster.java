public class folderMaster {
    private String fullFolderPath;

    public folderMaster(){
        this.fullFolderPath = System.getProperty("user.dir");
    }

    public String userDir(){
        return fullFolderPath;
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