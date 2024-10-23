import java.util.*;

public class Graph {
    HashMap<String, Actor> actors = new HashMap<>();    //string = actor nmid 
    ArrayList<Edge> edges = new ArrayList<>();

    HashMap<String, ArrayList<Edge>> graph = new HashMap<>();

    public void addActor(Actor actor){
        actors.put(actor.nmid, actor);
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public int amtOfNodes(){
        return actors.size();
    }
    public int amtOfEdges(){
        return edges.size();
    }

    public ArrayList<Actor> getConnections(Actor actor){
        ArrayList<Edge> edges;
        try{
           edges = graph.get(actor.nmid);
        }catch (Exception e){
            System.out.println("Actor not in graph");
            throw e;
        }
        ArrayList<Actor> connections = new ArrayList<>();
        for (Edge edge : edges){
            if (edge.actor1.equals(actor)){
                if (!connections.contains(edge.actor2)){connections.add(edge.actor2);}
            }else {if (!connections.contains(edge.actor1)){connections.add(edge.actor1);}}
        }
        return connections;
    }

    
}
