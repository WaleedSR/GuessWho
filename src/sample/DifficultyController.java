package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DifficultyController {

    public static int difficulty;

    /**
     * setting the game difficulty to easy
     * @param event
     * @throws IOException
     */
    @FXML
    private void setEasy(ActionEvent event) throws IOException {
        difficulty = 1;
        changeScene(event, "level_1.fxml");
    }

    /**
     * setting the game difficulty to medium
     * @param event
     * @throws IOException
     */
    @FXML
    private void setMedium(ActionEvent event) throws IOException {
        difficulty = 2;
        changeScene(event, "level_1.fxml");
    }

    /**
     * setting the game difficulty to hard
     * @param event
     * @throws IOException
     */
    @FXML
    private void setHard(ActionEvent event) throws IOException {
        difficulty = 3;
        changeScene(event, "level_1.fxml");
    }

    /**
     * get back to the main page (menu)
     * @param event
     * @throws IOException
     */
    @FXML
    private void getBack(ActionEvent event) throws IOException {
        changeScene(event, "menue.fxml");
    }

    private void changeScene(ActionEvent event, String fileName) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        window.setScene(new Scene(root));
        window.show();
        root.requestFocus();
    }
}