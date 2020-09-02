package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Character {
    private ImageView player;
    private int playerWidth = 30;
    private int playerHeight = 45;
    public static int lives = 3;
    private ArrayList<Image> rightImages;
    private ArrayList<Image> leftImages;
    private ArrayList<Image> upImages;
    private ArrayList<Image> downImages;

    /**
     *  Constructor
     */
    public Character() {
        rightImages = new ArrayList<>();
        leftImages = new ArrayList<>();
        upImages = new ArrayList<>();
        downImages = new ArrayList<>();
        player = new ImageView();
        player.setFitHeight(playerHeight);
        player.setFitWidth(playerWidth);
        fillImage();
        player = new ImageView(rightImages.get(0));
    }

    /**
     * Set the x-coordinate for the player character
     * @param x
     */
    public void setX(double x) {
        player.setX(x - playerWidth / 2 + 5);
    }

    /**
     * Set the y-coordinate for the player character
     * @param y
     */
    public void setY(double y) {
        player.setY(y - playerHeight + 12);
    }

    /**
     * Get the x-coordinate for the player character
     * @return
     */
    public double getX() {
        return player.getX() + playerWidth / 2 - 5;
    }

    /**
     * Get the y-coordinate for the player character
     * @return
     */
    public double getY() {
        return player.getY() + playerHeight - 12;
    }

    /**
     *
     * @return the player image
     */
    public ImageView getPlayer() {
        return player;
    }

    /**
     * change the animation of the player when moving right
     */
    public void changeImageRight() {
        Image next = rightImages.remove(0);
        player.setImage(next);
        rightImages.add(next);
    }

    /**
     * change the animation of the player when moving left
     */
    public void changeImageLeft() {
        Image next = leftImages.remove(0);
        player.setImage(next);
        leftImages.add(next);
    }

    /**
     * change the animation of the player when moving up
     */
    public void changeImageUp() {
        Image next = upImages.remove(0);
        player.setImage(next);
        upImages.add(next);
    }

    /**
     * change the animation of the player when moving down
     */
    public void changeImageDown() {
        Image next = downImages.remove(0);
        player.setImage(next);
        downImages.add(next);
    }

    /**
     * fill every ArrayList with images of specific direction
     * so that the player appears to be walking
     */
    private void fillImage() {
        Image toRight_1 = new Image(getClass().getResourceAsStream("icon/character/toRight_1.png"));
        Image toRight_2 = new Image(getClass().getResourceAsStream("icon/character/toRight_2.png"));
        Image toRight_3 = new Image(getClass().getResourceAsStream("icon/character/toRight_3.png"));
        Image toRight_4 = new Image(getClass().getResourceAsStream("icon/character/toRight_4.png"));
        rightImages.add(toRight_1);
        rightImages.add(toRight_2);
        rightImages.add(toRight_3);
        rightImages.add(toRight_4);

        Image toLeft_1 = new Image(getClass().getResourceAsStream("icon/character/toLeft_1.png"));
        Image toLeft_2 = new Image(getClass().getResourceAsStream("icon/character/toLeft_2.png"));
        Image toLeft_3 = new Image(getClass().getResourceAsStream("icon/character/toLeft_3.png"));
        Image toLeft_4 = new Image(getClass().getResourceAsStream("icon/character/toLeft_4.png"));
        leftImages.add(toLeft_1);
        leftImages.add(toLeft_2);
        leftImages.add(toLeft_3);
        leftImages.add(toLeft_4);

        Image toUP_1 = new Image(getClass().getResourceAsStream("icon/character/toUP_1.png"));
        Image toUP_2 = new Image(getClass().getResourceAsStream("icon/character/toUP_2.png"));
        Image toUP_3 = new Image(getClass().getResourceAsStream("icon/character/toUP_3.png"));
        Image toUP_4 = new Image(getClass().getResourceAsStream("icon/character/toUP_4.png"));
        upImages.add(toUP_1);
        upImages.add(toUP_2);
        upImages.add(toUP_3);
        upImages.add(toUP_4);

        Image toDown_1 = new Image(getClass().getResourceAsStream("icon/character/toDown_1.png"));
        Image toDown_2 = new Image(getClass().getResourceAsStream("icon/character/toDown_2.png"));
        Image toDown_3 = new Image(getClass().getResourceAsStream("icon/character/toDown_3.png"));
        Image toDown_4 = new Image(getClass().getResourceAsStream("icon/character/toDown_4.png"));
        downImages.add(toDown_1);
        downImages.add(toDown_2);
        downImages.add(toDown_3);
        downImages.add(toDown_4);
    }

    /**
     *
     * @return number of reduced lives
     */
    public int reduceLives() {
        lives -= 1;
        System.out.println(lives);
        return lives;
    }
}