package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenueController {

    @FXML
    public Button startButton;
    @FXML
    public Button helpButton;

    @FXML
    private void startGame(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("difficulty.fxml"));
        window.setScene(new Scene(root));
        window.show();
        root.requestFocus();
    }
}
