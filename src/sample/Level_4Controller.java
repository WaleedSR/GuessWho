package sample;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Level_4Controller extends setUp implements Initializable {

    @FXML
    public AnchorPane pane;
    @FXML
    public ImageView imageView;
    @FXML
    public ImageView lives;
    @FXML
    public Button muteButton;
    private Monster monster;
    private Monster monsterFollowPlayer;
    private Timer check;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
        lives.setImage(setUp.livesCount.getImage());
        setUp.livesCount = lives;
        livesCount = this.lives;
        monster = new Monster(0);
        monster.setX(2 * SIZE);
        monster.setY((16 * SIZE));
        pane.getChildren().add(monster.getCreature());
        monster.getCreature().setFitWidth(SIZE * 2);
        monster.getCreature().setFitHeight(SIZE * 2);
        monsterFollowPlayer = new Monster(3);
        monsterFollowPlayer.setX(16 * SIZE);
        monsterFollowPlayer.setY((16 * SIZE));
        pane.getChildren().add(monsterFollowPlayer.getCreature());
        monsterFollowPlayer.getCreature().setFitWidth(SIZE * 2);
        monsterFollowPlayer.getCreature().setFitHeight(SIZE * 2);
        monster.moveMonster();
        monsterFollowPlayer.moveMonsterFollowPlayer();
        checkMonsterTouchingPlayer();
    }

    /**
     * check if the monster is touching the player
     * in this case the number of player's lives is reduced by one
     * and the player character is set at the starting point
     */
    private void checkMonsterTouchingPlayer() {
        check = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (monster.getCreature().getBoundsInParent().intersects(character.getPlayer().getBoundsInParent())
                                && numGrid[(int) (monster.getY()) / SIZE - 2][(int) (monster.getX()) / SIZE] == 2 ||
                                monsterFollowPlayer.getCreature().getBoundsInParent().intersects(character.getPlayer().getBoundsInParent())
                                        && numGrid[(int) (monsterFollowPlayer.getY()) / SIZE - 2][(int) (monsterFollowPlayer.getX()) / SIZE] == 2) {
                            timer.stop();
                            if (checkLosingStatus()) {
                                monster.stopMonster();
                                monsterFollowPlayer.stopMonsterFollowPlayer();
                                stopChecking();
                            }
                            for (Rectangle rect : rectHolder) {
                                rect.setFill(Color.SILVER);
                                numGrid[(int) (rect.getY() / SIZE) - 2][(int) rect.getX() / SIZE] = 0;
                            }
                            rectHolder.clear();
                            character.setX((minX * SIZE));
                            character.setY((minY * SIZE));
                        } else if (!rectHolder.isEmpty()) {
                            try {
                                for (Rectangle held : rectHolder) {
                                    if (held.getBoundsInParent().intersects(monster.getCreature().getBoundsInParent()) ||
                                            held.getBoundsInParent().intersects(monsterFollowPlayer.getCreature().getBoundsInParent())) {
                                        timer.stop();
                                        if (checkLosingStatus()) {
                                            monster.stopMonster();
                                            monsterFollowPlayer.stopMonsterFollowPlayer();
                                            stopChecking();
                                        }
                                        for (Rectangle rect : rectHolder) {
                                            rect.setFill(Color.SILVER);
                                            numGrid[(int) (rect.getY() / SIZE - 2)][(int) rect.getX() / SIZE] = 0;
                                        }
                                        rectHolder.clear();
                                        character.setX((minX * SIZE));
                                        character.setY((minY * SIZE));
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                });
            }
        };
        check.schedule(task, 0, 1);
    }

    /**
     * stop checking if the monster touches the player
     * when the player loses
     */
    public void stopChecking() {
        check.cancel();
    }

    /**
     * move the character when the arrow keys are used
     * the character keeps moving in the specified direction until another key is used
     *
     * @param event
     */
    @FXML
    private void moveControl(KeyEvent event) throws IOException {
        switch (event.getCode()) {
            case RIGHT:
                timer.stop();
                timer.getKeyFrames().removeAll(up, right, down, left);
                timer.getKeyFrames().add(right);
                timer.setCycleCount(Animation.INDEFINITE);
                timer.play();
                break;

            case DOWN:
                timer.stop();
                timer.getKeyFrames().removeAll(up, right, down, left);
                timer.getKeyFrames().add(down);
                timer.setCycleCount(Animation.INDEFINITE);
                timer.play();
                break;

            case LEFT:
                timer.stop();
                timer.getKeyFrames().removeAll(up, right, down, left);
                timer.getKeyFrames().add(left);
                timer.setCycleCount(Animation.INDEFINITE);
                timer.play();
                break;

            case UP:
                timer.stop();
                timer.getKeyFrames().removeAll(up, right, down, left);
                timer.getKeyFrames().add(up);
                timer.setCycleCount(Animation.INDEFINITE);
                timer.play();
                break;
        }
    }

    /**
     * reset the player's lives to three
     * and show the main page
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void startAgain(Event event) throws IOException {
        changeScene(event, "difficulty.fxml");
        character.lives = 3;
    }

    /**
     * mute/unmute the background music
     */
    @FXML
    public void mute() {
        if (muteButton.getText().equals("Mute")) {
            muteButton.setId("muted");
            muteButton.setText("Unmute");
            Main.mediaPlayer.setMute(true);
        } else {
            muteButton.setId("unmuted");
            muteButton.setText("Mute");
            Main.mediaPlayer.setMute(false);
        }
    }
}