package dk.easv.mrs.BLL.util;

// projects imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.DB.MovieDAO_Db;
// java imports
import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {


    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie : searchBase) {
            if(compareToMovieTitle(query, movie) || compareToMovieYear(query, movie))
            {
                searchResult.add(movie);
            }
        }

        return searchResult;
    }

    public List<Movie> search(MovieDAO_Db searchBase, String query) throws Exception {
        try{
            Integer.parseInt(query);
            return searchBase.getMovies(query, "Year");
        } catch (Exception e) {
            return searchBase.getMovies(query, "Title");
        }

    }

    private boolean compareToMovieYear(String query, Movie movie) {
        return Integer.toString(movie.getYear()).contains(query);
    }

    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

}
