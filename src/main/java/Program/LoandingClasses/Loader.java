package Program.LoandingClasses;

public class Loader {

    public static String loadFile(){
        String file = FileLoader.fileInitialization();
        return  file;
    }

    public static String parseNeeds(){
       String needsParse = loadFile();
       if(needsParse.equals("")){
           return "";
       } else {
           return needsParse;
       }
    }
}
