import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException; 
import java.util.Scanner; 

public class FilHjelper {
    Scanner lesFraFil(String filnavn){
        try{
            File fil = new File(filnavn);
            Scanner leser = new Scanner(fil);
            return leser;
        }
        catch (FileNotFoundException e){
            System.out.println("Fant ikke fil");
            e.printStackTrace();
        }
        return null;
    }

    void skrivTilFil(String filnavn, ArrayList<Integer> a){
        try {
            FileWriter IsortertFil = new FileWriter(filnavn + "_out");
            for (int tall : a){
                String t = String.valueOf(tall);
                IsortertFil.write(t + "\n");
            }
            IsortertFil.close();

        } catch (IOException e) {
            System.out.println("noe gikk galt med skriving til fil");
            e.printStackTrace();
        }
    }
}
