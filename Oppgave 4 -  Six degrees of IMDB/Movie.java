public class Movie{
    String ttid;
    String name;
    double rating;
   
    public Movie(String ttid, String name, double rating){
        this.ttid = ttid;
        this.name = name;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name;
    }
}