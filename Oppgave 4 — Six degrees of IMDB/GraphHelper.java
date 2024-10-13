import java.util.*;
import java.io.*;
import java.util.stream.Stream;
import java.nio.file.*;

public class GraphHelper {
    static Graph graph = new Graph();

    public Graph GraphBuilder(String moviesFilePath, String actorsFilePath){
        HashMap<String, Movie> movies = importMovies(moviesFilePath);
        HashMap<String, Actor> actors = importActors(actorsFilePath);
        graph = buildGraph(movies, actors);
        return graph;
    }

    public static HashMap<String, Movie> importMovies(String filename) {
        HashMap<String, Movie> movies = new HashMap<>();

        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.forEach(line -> {
                String[] linesplit = line.split("\t");
                String ttid = linesplit[0];
                String name = linesplit[1];
                double rating = Double.parseDouble(linesplit[2]);
                movies.put(ttid, new Movie(ttid, name, rating));
            });
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return movies;
    }

    public static HashMap<String, Actor> importActors(String filename) {
        HashMap<String, Actor> actors = new HashMap<>();
        
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.forEach(line -> {
                String[] linesplit = line.split("\t");
                String nmid = linesplit[0];
                String name = linesplit[1];

                ArrayList<String> actorMovies = new ArrayList<>();
                for (int i = 2; i < linesplit.length; i++) {
                    actorMovies.add(linesplit[i]);
                }

                actors.put(nmid, new Actor(nmid, name, actorMovies));
                graph.graph.put(nmid, new ArrayList<>());

            });
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        return actors;
    }

    public static Graph buildGraph(HashMap<String, Movie> movies, HashMap<String, Actor> actors) {
        graph.actors = actors;
        long start = System.nanoTime();
        // Make a hashmap with with movie as key and actors as values
        HashMap<String, List<Actor>> movieToActors = new HashMap<>();
        for (Actor actor : actors.values()) {
            for (String movieId : actor.ttids) {
                List<Actor> actorsInMovie = movieToActors.get(movieId);
                if (actorsInMovie == null){
                    actorsInMovie = new ArrayList<>();
                }
                actorsInMovie.add(actor);
                movieToActors.put(movieId, actorsInMovie);
            }
        }

        movies.keySet().parallelStream().forEach(ttid -> {
            List<Actor> actorsInMovie = movieToActors.get(ttid);
            if (actorsInMovie != null && actorsInMovie.size() > 1) {
                // Iterate over pairs of actors without modifying the original list
                for (int i = 0; i < actorsInMovie.size(); i++) {
                    for (int j = i + 1; j < actorsInMovie.size(); j++) {
                        Actor actor = actorsInMovie.get(i);
                        Actor otherActor = actorsInMovie.get(j);
                        Edge edge = new Edge(movies.get(ttid), actor, otherActor);
                        graph.graph.get(actor.nmid).add(edge);
                        graph.graph.get(otherActor.nmid).add(edge);
                        synchronized (GraphHelper.class) {
                            graph.addEdge(edge);
                        }
                    }
                }
            }
        });
        
        long end = System.nanoTime();
        long elapsedTime = end - start; 
        double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
        System.out.println("Time to build graph: " + elapsedTimeInSecond);
        return graph;
    }

    ArrayList<Actor> findPathTo(Graph graph, Actor a, Actor b){
        HashSet<Actor> visited = new HashSet<>();
        visited.add(a);
        LinkedList<Actor> queue = new LinkedList<>();
        queue.add(a);

        HashMap<Actor, Actor> pathhelper = new HashMap<>();
        pathhelper.put(a, null);
        boolean found = false;

        while (queue.size() > 0 && !found){
            Actor u = queue.removeFirst();
            for (Actor coActor : graph.getConnections(u)){
                if (!visited.contains(coActor)){
                    pathhelper.put(coActor, u);
                    if(coActor.equals(b)){
                        found = true;
                        break;
                    }
                    visited.add(coActor);
                    queue.add(coActor);
                }
            }
        }

        if (!found) {
            // If there's no path found - return an empty list.
            System.out.println("Could not find path");
            return new ArrayList<>();
        }
    
        LinkedList<Actor> path = new LinkedList<>();
        path.add(b);
        Actor next = pathhelper.get(b);
        while(next!= null){
            path.add(next);
            next = pathhelper.get(next);
        }
        
        Collections.reverse(path);
        return new ArrayList<>(path);
    }

    String getPath(Graph graph, Actor a, Actor b){
        ArrayList<Actor> path = findPathTo(graph, a, b);
        String print = "";
        print += path.get(0);
        Movie movieBetween = null;
        for(int i = 0; i < path.size()-1; i++){
            for (Edge edge : graph.graph.get(path.get(i).nmid)){
                if ((edge.actor1.equals(path.get(i)) && edge.actor2.equals(path.get(i+1))) || (edge.actor2.equals(path.get(i)) && edge.actor1.equals(path.get(i+1)))){
                    movieBetween = edge.movie;
                }
            }
            print += " -> " + movieBetween + " (" + movieBetween.rating + ") -> " + path.get(i+1);
        }
        return print;
    }
}