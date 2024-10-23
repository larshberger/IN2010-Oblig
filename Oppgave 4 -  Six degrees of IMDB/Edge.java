public class Edge {
    Actor actor1;
    Actor actor2;
    Movie movie;

    public Edge(Movie movie, Actor actor1, Actor actor2){
        this.movie = movie;
        this.actor1 = actor1;
        this.actor2 = actor2;
    }
}
