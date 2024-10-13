import java.util.ArrayList;
import java.util.Scanner; 
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> ar = new ArrayList<Integer>();    
        MergeSort M = new MergeSort();

        //Leser inn alle tallene fra fil
        String filnavn = args[0];
       
        FilHjelper filhjelper = new FilHjelper();
        Scanner leser = filhjelper.lesFraFil(filnavn);
        
        while (leser.hasNextInt()){
            int tall = leser.nextInt();
            ar.add(tall);
            ar = M.MergeSort(ar);
        }

        filhjelper.skrivTilFil(filnavn, ar);
    }
}
