package Program.SchoolNeeds;

import java.util.TreeMap;

public class Test extends Need {
    private final boolean IS_IMPORTANT = true;
    private static TreeMap<String,Test> needTreeSet;

    public boolean isIS_IMPORTANT() {
        return IS_IMPORTANT;
    }

    public static TreeMap<String, Test> getTestTreeSet() {
        return needTreeSet;
    }

    public static void setTestTreeSet(Test test, String s) {
        needTreeSet.put(s,test);
    }

    public static void maidTreeMap(){
        needTreeSet = new TreeMap<>();
    }


}
