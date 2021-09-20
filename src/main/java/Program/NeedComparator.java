package Program;

import Program.SchoolNeeds.Need;

import java.util.Comparator;

public class NeedComparator implements Comparator<Need> {
    @Override
    public int compare(Need need, Need t1) {
        if(need.getImplementationDate().after(t1.getImplementationDate())){
            return 1;
        } else {
            return -1;
        }
    }
}
