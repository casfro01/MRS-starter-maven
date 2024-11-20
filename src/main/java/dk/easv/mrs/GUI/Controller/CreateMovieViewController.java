package dk.easv.mrs.GUI.Controller;

// project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
// java imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateMovieViewController {
    @FXML private TextField titletxt;
    @FXML private TextField yeartxt;
    @FXML private Label errorlbl;

    private Stage stage;
    private MovieModel movieModel;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    @FXML
    private void cancleThing(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    private void createMovie(ActionEvent actionEvent) throws Exception {
        String title = titletxt.getText();
        if (title.trim().isEmpty()) {
            errorlbl.setText("Title is empty.");
            return;
        }
        else if (yeartxt.getText().trim().isEmpty()) {
            errorlbl.setText("Year is empty.");
            return;
        }
        try{
            int year = Integer.parseInt(yeartxt.getText());
            movieModel.addMovie(new Movie(-1, year, title));
            stage.close();
        } catch (NumberFormatException e) {
            errorlbl.setText("Year is not a number, you idiot!");
            throw new RuntimeException(e);
        }
    }
}
