package dk.easv.mrs.BLL;
// project import
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.util.MovieSearcher;
import dk.easv.mrs.DAL.DB.MovieDAO_Db;
import dk.easv.mrs.DAL.IMovieDataAccess;
import dk.easv.mrs.DAL.MovieDAO_File;
// java import
import java.util.List;

public class MovieManager {

    private MovieSearcher movieSearcher = new MovieSearcher();
    private IMovieDataAccess movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO_Db();
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws Exception {

        if (movieDAO.getClass() == MovieDAO_Db.class) {
            return movieSearcher.search((MovieDAO_Db) movieDAO, query);
        }
        else{ // database search
            List<Movie> allMovies = getAllMovies();
            List<Movie> searchResult = movieSearcher.search(allMovies, query);
            return searchResult;
        }
    }

    public Movie addMovie(Movie newMovie) throws Exception
    {
        return movieDAO.createMovie(newMovie);
    }

    public void updateMovie(Movie movie) throws Exception {
        movieDAO.updateMovie(movie);
    }
}
