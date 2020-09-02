package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConfimBox {
    /**
     * confirm box for when the user tries to close the program
     * so that this doesn't happen by mistake
     * @return
     */
    public static boolean display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AtomicBoolean answer = new AtomicBoolean(false);
        VBox layout = new VBox();
        Text text = new Text("Are you sure that you want to close the program?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        layout.getChildren().addAll(text, yesButton, noButton);
        yesButton.setOnAction(e -> {
            answer.set(true);
            window.close();
        });
        noButton.setOnAction(e -> {
            answer.set(false);
            window.close();
        });

        layout.setAlignment(Pos.CENTER);
        text.setFill(Paint.valueOf("#f2aa26"));
        yesButton.setPrefWidth(100);
        yesButton.setPrefHeight(15);
        noButton.setPrefWidth(100);
        noButton.setPrefHeight(15);
        layout.setSpacing(8);
        layout.getStylesheets().add(ConfimBox.class.getResource("CSS/buttons.css").toExternalForm());
        layout.getStylesheets().add(ConfimBox.class.getResource("CSS/confirmBox.css").toExternalForm());

        window.setTitle("Guess Who");
        window.setScene(new Scene(layout));
        window.showAndWait();

        return answer.get();
    }

}
