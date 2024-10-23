import java.io.FileNotFoundException; 
import java.util.Scanner; 
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String moviesFileName = "six-degrees-of-imdb-ressursside/movies.tsv";
        String actorsFileName = "six-degrees-of-imdb-ressursside/actors.tsv";
        GraphHelper gb = new GraphHelper();
        Graph graph = gb.GraphBuilder(moviesFileName, actorsFileName);

        System.out.println("Nodes: " + graph.amtOfNodes());
        System.out.println("Edges: " + graph.amtOfEdges() + "\n");
        
        try{
            File file = new File("data.tsv");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                String[] split = line.split("\t");
                System.out.println(gb.getPath(graph, graph.actors.get(split[0]), graph.actors.get(split[1])) + "\n" );
            }
            
            reader.close();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            e.printStackTrace();
        }

    }
}
