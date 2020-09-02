package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {
    public static MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menue.fxml"));
        primaryStage.setTitle("Guess Who");
        Image icon = new Image(getClass().getResourceAsStream("icon/icon.png"));
        Media music = new Media(getClass().getResource("audio/Wacky_Races.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaxHeight(625);
        primaryStage.setMaxWidth(825);
        primaryStage.show();
        root.requestFocus();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            exit(primaryStage);
        });
    }

    private void exit(Stage stage) {

        boolean answer = ConfimBox.display();

        if (answer) {
            stage.close();
        }
    }
}