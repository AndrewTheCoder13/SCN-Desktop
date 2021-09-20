package Program.SchoolNeeds;

import java.util.TreeMap;

public class HomeWork extends Need {
    private static TreeMap<String,HomeWork> needTreeSet;

    public static TreeMap<String, HomeWork> getHWTreeSet() {
        return needTreeSet;
    }

    public static void setHWTreeSet(HomeWork test, String s) {
        needTreeSet.put(s,test);
    }
    public static void maidTreeMap(){
        needTreeSet = new TreeMap<>();
    }


}
