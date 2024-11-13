package dk.easv.mrs.GUI.Controller;

// project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
// java imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {

    @FXML private TextField txtMovieSearch;
    @FXML private ListView<Movie> lstMovies;
    private MovieModel movieModel;
    @FXML
    private Button updatebtn;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                updatebtn.setVisible(false);
                updatebtn.setDisable(true);
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void refresh(){
        lstMovies.refresh();
    }

    @FXML
    private void createNewMovie(ActionEvent actionEvent) throws IOException {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("/views/CreateMovieView.fxml"));

        Parent scene = Loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Create Movie");

        stage.initModality(Modality.APPLICATION_MODAL);
        CreateMovieViewController controller = Loader.getController();
        controller.setStage(stage);
        controller.setMovieModel(movieModel);
        stage.show();
    }

    @FXML
    private void itemSelected(MouseEvent mouseEvent) {

        if (lstMovies.getSelectionModel().getSelectedItem() != null) {
            updatebtn.setVisible(true);
            updatebtn.setDisable(false);
        }
    }

    @FXML
    private void updateMovie(ActionEvent actionEvent) throws IOException {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("/views/updateMovieView.fxml"));

        Parent scene = Loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(scene));
        stage.setTitle("Update Movie");

        stage.initModality(Modality.APPLICATION_MODAL);
        UpdateMovieViewController controller = Loader.getController();
        controller.setStage(stage);
        controller.setMovieModel(movieModel);
        controller.setMovie(lstMovies.getSelectionModel().getSelectedItem());
        controller.setTextFields();
        controller.setMovieController(this);
        stage.show();
    }
}
