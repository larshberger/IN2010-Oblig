import java.util.*;

public class Actor {
    String nmid;
    String name;
    ArrayList<String> ttids;
    public Actor(String nmid, String name, ArrayList<String> ttids){
        this.nmid = nmid; 
        this.name = name;
        this.ttids = ttids;
    }

    @Override
    public String toString() {
        return name;
    }
}
