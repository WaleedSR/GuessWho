package sample;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class setUp {
    public AnchorPane pane = new AnchorPane();
    public final static int SIZE = 15;
    private static double imageViewWidthAndHeight = 500.0;
    public static Rectangle[][] rectGrid;
    public int[][] numGrid;
    public static Character character = new Character();
    public ArrayList<Rectangle> rectHolder;
    public static int minX;
    public static int minY;

    protected Timeline timer;
    protected KeyFrame right;
    protected KeyFrame left;
    protected KeyFrame up;
    protected KeyFrame down;

    public static ImageView livesCount = new ImageView();
    public ImageView winOrLose = new ImageView();
    public Button startAgain = new Button();
    public Button nextLevel = new Button();

    /**
     * initializes the game by creating the rectangles and the player character
     *
     */
    protected void initialize() {
        rectHolder = new ArrayList<>();
        rectGrid = new Rectangle[(int) imageViewWidthAndHeight / SIZE + 2][(int) (imageViewWidthAndHeight / SIZE + 2)];
        numGrid = new int[(int) imageViewWidthAndHeight / SIZE + 2][(int) (imageViewWidthAndHeight / SIZE + 2)];
        minX = 0;
        minY = 2;

        timer = new Timeline();
        left = new KeyFrame(Duration.millis(135), event -> {
            moveLeft();
        });
        right = new KeyFrame(Duration.millis(135), event -> {
            moveRight();
        });
        up = new KeyFrame(Duration.millis(135), event -> {
            moveUp();
        });
        down = new KeyFrame(Duration.millis(135), event -> {
            moveDown();
        });

        for (int i = 0; i < rectGrid.length; i++) {
            for (int j = 0; j < rectGrid.length; j++) {
                Rectangle rect = new Rectangle(SIZE, SIZE);
                rect.setFill(Color.SILVER);
                rect.setStyle("-fx-opacity: 0.98;");
                rect.setY(minY * SIZE);
                rect.setX(minX * SIZE);
                rectGrid[i][j] = rect;
                pane.getChildren().add(rect);
                minX++;
            }
            minY++;
            minX = 0;
        }
        minX = 0;
        minY = 2;

        for (int i = 0; i < imageViewWidthAndHeight / SIZE + 1; i++) {
            Rectangle a = rectGrid[i][0];
            a.setId("border");
            Rectangle b = rectGrid[0][i];
            b.setId("border");
            Rectangle c = rectGrid[i][(int) imageViewWidthAndHeight / SIZE + 1];
            c.setId("border");
            Rectangle d = rectGrid[(int) imageViewWidthAndHeight / SIZE + 1][i];
            d.setId("border");
            a.setFill(Color.WHITE);
            b.setFill(Color.WHITE);
            c.setFill(Color.WHITE);
            d.setFill(Color.WHITE);

            numGrid[i][0] = 1;
            numGrid[0][i] = 1;
            numGrid[i][(int) imageViewWidthAndHeight / SIZE + 1] = 1;
            numGrid[(int) imageViewWidthAndHeight / SIZE + 1][i] = 1;
        }

        character.setX((minX * SIZE));
        character.setY((minY * SIZE));
        pane.getChildren().add(character.getPlayer());
        character.getPlayer().setFitHeight(SIZE * 3);
        character.getPlayer().setFitWidth(SIZE * 2);
    }

    /**
     * move the character down
     */
    protected void moveDown() {
        if (character.getY() < rectGrid[rectGrid.length - 1][0].getY()) {
            if (character.getY() < rectGrid[rectGrid.length - 1][0].getY()) {
                character.changeImageDown();
                character.setY(character.getY() + SIZE);
                if (checkPlayerStatus()) {
                    if (!checkRectInNextStep()) {
                        try {
                            Rectangle corner1 = rectGrid[(int) rectHolder.get(rectHolder.size() - 1).getY() / SIZE - 2][(int) rectHolder.get(0).getX() / SIZE];
                            rectHolder.add(corner1);
                            Polygon poly = new Polygon();
                            for (Rectangle heldRect : rectHolder) {
                                if (rectHolder.get(0).getX() < rectHolder.get(rectHolder.size() - 2).getX()) {
                                    poly.getPoints().addAll(heldRect.getX(), heldRect.getY() + SIZE);
                                    numGrid[(int) heldRect.getY() / SIZE - 2][(int) heldRect.getX() / SIZE] = 1;
                                    heldRect.setStyle("-fx-opacity: 0");
                                } else {
                                    poly.getPoints().addAll(heldRect.getX() + SIZE, heldRect.getY() + SIZE);
                                    numGrid[(int) heldRect.getY() / SIZE - 2][(int) heldRect.getX() / SIZE] = 1;
                                    heldRect.setStyle("-fx-opacity: 0");
                                }
                                heldRect.setFill(Color.SILVER);
                            }
                            poly.setStyle("-fx-opacity: 0");
                            rectHolder.clear();
                            for (Rectangle[] rr : rectGrid) {
                                for (Rectangle rg : rr) {
                                    if (poly.contains(rg.getX(), rg.getY())) {
                                        rg.setStyle("-fx-opacity: 0");
                                        numGrid[(int) (rg.getY() / SIZE) - 2][(int) (rg.getX() / SIZE)] = 1;
                                    }
                                }
                            }
                            checkWinningStatus();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

    /**
     * move the character up
     */
    protected void moveUp() {
        character.changeImageUp();
        if (character.getY() > rectGrid[0][0].getY()) {
            character.setY(character.getY() - SIZE);
            if (checkPlayerStatus()) {
                if (!checkRectInNextStep()) {
                    try {
                        Rectangle corner1 = rectGrid[(int) rectHolder.get(rectHolder.size() - 1).getY() / SIZE - 2][(int) rectHolder.get(0).getX() / SIZE];
                        rectHolder.add(corner1);
                        Polygon poly = new Polygon();
                        for (Rectangle heldRect : rectHolder) {
                            if (rectHolder.get(0).getX() > rectHolder.get(rectHolder.size() - 2).getX()) {
                                poly.getPoints().addAll(heldRect.getX() + SIZE, heldRect.getY());
                            }
                            poly.getPoints().addAll(heldRect.getX(), heldRect.getY());
                            numGrid[(int) heldRect.getY() / SIZE - 2][(int) heldRect.getX() / SIZE] = 1;
                            heldRect.setStyle("-fx-opacity: 0");
                            heldRect.setFill(Color.SILVER);
                        }
                        poly.setStyle("-fx-opacity: 0");
                        rectHolder.clear();
                        for (Rectangle[] rr : rectGrid) {
                            for (Rectangle rg : rr) {
                                if (poly.contains(rg.getX(), rg.getY())) {
                                    rg.setStyle("-fx-opacity: 0");
                                    numGrid[(int) (rg.getY() / SIZE) - 2][(int) (rg.getX() / SIZE)] = 1;
                                }
                            }
                        }
                        checkWinningStatus();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /**
     * move the character left
     */
    protected void moveLeft() {
        character.changeImageLeft();
        if (character.getX() > rectGrid[0][0].getX()) {
            character.setX(character.getX() - SIZE);
            if (checkPlayerStatus()) {
                if (!checkRectInNextStep()) {
                    try {
                        Rectangle corner2 = rectGrid[(int) rectHolder.get(0).getY() / SIZE - 2][(int) rectHolder.get(rectHolder.size() - 1).getX() / SIZE];
                        rectHolder.add(corner2);
                        Polygon poly = new Polygon();
                        for (Rectangle heldRect : rectHolder) {
                            if (rectHolder.get(0).getY() > rectHolder.get(rectHolder.size() - 2).getY()) {
                                poly.getPoints().addAll(heldRect.getX(), heldRect.getY() + SIZE);
                            }
                            poly.getPoints().addAll(heldRect.getX(), heldRect.getY());
                            numGrid[(int) heldRect.getY() / SIZE - 2][(int) heldRect.getX() / SIZE] = 1;
                            heldRect.setStyle("-fx-opacity: 0");
                            heldRect.setFill(Color.SILVER);
                        }
                        poly.setStyle("-fx-opacity: 0");
                        rectHolder.clear();
                        for (Rectangle[] rr : rectGrid) {
                            for (Rectangle rg : rr) {
                                if (poly.contains(rg.getX(), rg.getY())) {
                                    rg.setStyle("-fx-opacity: 0");
                                    numGrid[(int) (rg.getY() / SIZE) - 2][(int) (rg.getX() / SIZE)] = 1;
                                }
                            }
                        }
                        checkWinningStatus();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /**
     * move the character right
     */
    protected void moveRight() {
        if (character.getX() < rectGrid[0][rectGrid.length - 1].getX()) {
            character.changeImageRight();
            if (character.getX() < rectGrid[0][rectGrid.length - 1].getX()) {
                character.setX(character.getX() + SIZE);
                if (checkPlayerStatus()) {
                    if (!checkRectInNextStep()) {
                        try {
                            Rectangle corner2 = rectGrid[(int) rectHolder.get(0).getY() / SIZE - 2][(int) rectHolder.get(rectHolder.size() - 1).getX() / SIZE];
                            rectHolder.add(corner2);
                            Polygon poly = new Polygon();
                            for (Rectangle heldRect : rectHolder) {
                                if (rectHolder.get(0).getY() < rectHolder.get(rectHolder.size() - 2).getY()) {
                                    poly.getPoints().addAll(heldRect.getX() + SIZE, heldRect.getY());
                                } else if (rectHolder.get(0).getY() > rectHolder.get(rectHolder.size() - 2).getY()) {
                                    poly.getPoints().addAll(heldRect.getX() + SIZE, heldRect.getY() + SIZE);
                                }
                                numGrid[(int) heldRect.getY() / SIZE - 2][(int) heldRect.getX() / SIZE] = 1;
                                heldRect.setStyle("-fx-opacity: 0");
                                heldRect.setFill(Color.SILVER);
                            }
                            poly.setStyle("-fx-opacity: 0");
                            rectHolder.clear();
                            for (Rectangle[] rr : rectGrid) {
                                for (Rectangle rg : rr) {
                                    if (poly.contains(rg.getX(), rg.getY())) {
                                        rg.setStyle("-fx-opacity: 0");
                                        numGrid[(int) (rg.getY() / SIZE) - 2][(int) (rg.getX() / SIZE)] = 1;
                                    }
                                }
                            }
                            checkWinningStatus();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

    /**
     * check if the player wallks over the same path
     * before revealing that side of the picture
     *
     * @return
     */
    private boolean checkPlayerStatus() {
        if (numGrid[(int) (character.getY() / SIZE) - 2][(int) (character.getX() / SIZE)] == 2) {
            timer.stop();
            checkLosingStatus();
            for (Rectangle rect : rectHolder) {
                rect.setFill(Color.SILVER);
                numGrid[(int) (rect.getY() / SIZE) - 2][(int) rect.getX() / SIZE] = 0;
            }
            rectHolder.clear();
            character.setX((minX * SIZE));
            character.setY((minY * SIZE));
            return false;
        } else {
            return true;
        }
    }

    /**
     * check if the player has revealed all the picture
     */
    protected void checkWinningStatus() {
        int numOfClearedRects = 0;
        for (int[] outerArray : numGrid) {
            for (int value : outerArray) {
                if (value == 1) {
                    numOfClearedRects++;
                    if (numOfClearedRects == rectGrid.length * rectGrid.length) {
                        Image win = new Image(getClass().getResourceAsStream("icon/youWin.png"));
                        winOrLose.setImage(win);
                        nextLevel.setDisable(false);
                        timer.getKeyFrames().removeAll(right,left,up,down);
                        timer.stop();
                    }
                }
            }
        }
    }

    /**
     * reduce number of player's lives
     * and check if the player has any life left
     *
     * @return
     */
    protected boolean checkLosingStatus() {
        int remainingLives = character.reduceLives();
        if (remainingLives == 2) {
            Image img = new Image(getClass().getResourceAsStream("icon/lives_2.png"));
            livesCount.setImage(img);
            return false;
        } else if (remainingLives == 1) {
            Image img = new Image(getClass().getResourceAsStream("icon/lives_1.png"));
            livesCount.setImage(img);
            return false;
        } else {
            Image img = new Image(getClass().getResourceAsStream("icon/lives_0.png"));
            livesCount.setImage(img);
            Image lose = new Image(getClass().getResourceAsStream("icon/gameOver.png"));
            winOrLose.setImage(lose);
            startAgain.setDisable(false);
            timer.getKeyFrames().removeAll(right,left,up,down);
            timer.stop();
            return true;
        }
    }

    /**
     * check if next rectangle that the player walks on
     * is a silver rectangle
     *
     * @return
     */
    private boolean checkRectInNextStep() {
        if (numGrid[(int) (character.getY() / SIZE) - 2][(int) (character.getX() / SIZE)] == 0) {
            numGrid[(int) (character.getY() / SIZE) - 2][(int) (character.getX() / SIZE)] = 2;
            Rectangle heldRect = rectGrid[(int) (character.getY() / SIZE) - 2][(int) (character.getX() / SIZE)];
            heldRect.setFill(Color.GREEN);
            rectHolder.add(heldRect);
            return true;
        } else {
            return false;
        }
    }

    /**
     * change the scene to the next level if the player wins or to
     * the main page when the player loses
     *
     * @param event
     * @param file
     * @throws IOException
     */
    public void changeScene(Event event, String file) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(file));
        window.setScene(new Scene(root));
        root.requestFocus();
        window.show();
    }
}