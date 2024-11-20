package dk.easv.mrs.GUI.Model;

// project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;
// java imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }


    public void addMovie(Movie newMovie) throws Exception
    {
        moviesToBeViewed.add(movieManager.addMovie(newMovie));
    }

    public void changeMovie(Movie movie) throws Exception {
        // update the file
        movieManager.updateMovie(movie);
    }

    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }
}
