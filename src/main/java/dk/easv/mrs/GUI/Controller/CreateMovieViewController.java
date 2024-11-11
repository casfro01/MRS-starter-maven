package dk.easv.mrs.GUI.Controller;
// project imports
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void cancleThing(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    private void createMovie(ActionEvent actionEvent) {
    }
}
