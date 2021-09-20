package Program.LoandingClasses;

import Program.Main;
import org.json.simple.JSONArray;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class FileLoader {
    private static File needFile = new File("src/main/resources/needs.json");

    public static String fileInitialization(){
        URL url = FileLoader.class.getResource("");
        try {
            needFile = new File(url.toURI().getPath() + "needs.json");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(needFile.exists()){
            String fileParse  = fileParse();
            return fileParse;
        } else {
           fileCreation();
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(needFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer.write("[]");
            writer.flush();
            writer.close();
            return "";
        }
    }

    public static String fileParse(){
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(needFile));
            for(;;){
                String line = reader.readLine();
                if(line.equals(null)){
                    break;
                }
                builder.append(line + "\n");
            }

        } catch (Exception ex){

        }
        return  builder.toString();
    }

    public static void fileCreation(){
        try {
            File needFile = new File("src/main/resources");
            if (needFile.exists()) {
                File file = new File(needFile, "needs.json");
                file.createNewFile();
            } else {
                needFile.mkdir();
                File file = new File(needFile, "needs.json");
                file.createNewFile();

            }
        } catch (Exception e){

        }
    }
}
