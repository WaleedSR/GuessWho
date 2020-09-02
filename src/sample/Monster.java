package sample;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Monster {
    private ImageView creature;
    private ArrayList<Image> ghostImages;
    private int bounceX = setUp.SIZE;
    private int bounceY = setUp.SIZE;
    private int monsterSpeed;
    private Timer monster1;
    private Timer monster2;
    private Timer monster3;
    private Timer monster4;

    /**
     * Constructor
     *
     * @param monsterNum
     */
    public Monster(int monsterNum) {
        ghostImages = new ArrayList<>();
        fillImage();
        creature = new ImageView(ghostImages.get(monsterNum));
        if (DifficultyController.difficulty == 1) {
            monsterSpeed = 300;
        } else if (DifficultyController.difficulty == 2) {
            monsterSpeed = 200;
        } else {
            monsterSpeed = 100;
        }
    }

    /**
     * Set the x-coordinate for the monster
     *
     * @param x
     */
    public void setX(double x) {
        creature.setX(x);
    }

    /**
     * Set the y-coordinate for the monster
     *
     * @param y
     */
    public void setY(double y) {
        creature.setY(y);
    }

    /**
     * get the x-coordinate for the monster
     */
    public double getX() {
        return creature.getX();
    }

    /**
     * get the y-coordinate for the monster
     */
    public double getY() {
        return creature.getY();
    }

    /**
     * @return the monster image
     */
    public ImageView getCreature() {
        return creature;
    }

    /**
     * continuously move the monster so that it
     * bounces off all four sides
     */
    public void moveMonster() {
        monster1 = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        boolean right = creature.getX() > (setUp.rectGrid.length - 4) * setUp.SIZE;
                        boolean up = creature.getY() - setUp.SIZE < setUp.minY + setUp.SIZE * 2;
                        boolean left = creature.getX() < 2 * setUp.SIZE;
                        boolean down = creature.getY() + setUp.SIZE * 2 > setUp.rectGrid.length * setUp.SIZE;

                        if (right) {
                            bounceX = -1 * bounceX;
                        }
                        if (up) {
                            bounceY = -1 * bounceY;
                        }
                        if (left) {
                            bounceX = -1 * bounceX;
                        }
                        if (down) {
                            bounceY = -1 * bounceY;
                        }
                        creature.setX(creature.getX() + bounceX);
                        creature.setY(creature.getY() + bounceY);
                    }
                });
            }
        };
        monster1.schedule(task, 0, monsterSpeed);
    }

    /**
     * continuously move the monster vertically
     */
    public void moveMonsterVertically() {
        monster2 = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        boolean up = creature.getY() - setUp.SIZE < setUp.minY + setUp.SIZE * 2;
                        boolean down = creature.getY() + setUp.SIZE * 2 > setUp.rectGrid.length * setUp.SIZE;

                        if (up) {
                            bounceY = -1 * bounceY;
                        }
                        if (down) {
                            bounceY = -1 * bounceY;
                        }
                        creature.setY(creature.getY() + bounceY);
                    }
                });
            }
        };
        monster2.schedule(task, 0, monsterSpeed);
    }

    /**
     * continuously move the monster horizontally
     */
    public void moveMonsterHorizontally() {
        monster3 = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        boolean right = creature.getX() > (setUp.rectGrid.length - 4) * setUp.SIZE;
                        boolean left = creature.getX() - setUp.SIZE < setUp.SIZE;
                        if (right) {
                            bounceX = -1 * bounceX;
                        }
                        if (left) {
                            bounceX = -1 * bounceX;
                        }
                        creature.setX(creature.getX() + bounceX);
                    }
                });
            }
        };
        monster3.schedule(task, 0, monsterSpeed);
    }

    /**
     * continuously move the monster so that it follows tha player
     */
    public void moveMonsterFollowPlayer() {
        monster4 = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        boolean right = creature.getX() > (setUp.rectGrid.length - 4) * setUp.SIZE;
                        boolean up = creature.getY() - setUp.SIZE < setUp.minY + setUp.SIZE * 2;
                        boolean left = creature.getX() < 2 * setUp.SIZE;
                        boolean down = creature.getY() + setUp.SIZE * 2 > setUp.rectGrid.length * setUp.SIZE;

                        if (!left && setUp.character.getX() < creature.getX()) {
                            bounceX = -1 * bounceX;
                            creature.setX(creature.getX() + bounceX);
                        } else if (!right && setUp.character.getX() > creature.getX()) {
                            bounceX = 1 * bounceX;
                            creature.setX(creature.getX() + bounceX);
                        }
                        if (!down && setUp.character.getY() > creature.getY()) {
                            bounceY = 1 * bounceY;
                            creature.setY(creature.getY() + bounceY);
                        } else if (!up && setUp.character.getY() < creature.getY()) {
                            bounceY = -1 * bounceY;
                            creature.setY(creature.getY() + bounceY);
                        }
                        bounceX = setUp.SIZE;
                        bounceY = setUp.SIZE;
                    }
                });
            }
        };
        monster4.schedule(task, 0, 500);
    }

    /**
     * stop the monster from moving
     */
    public void stopMonster() {
        monster1.cancel();
    }

    /**
     * stop the monster from moving
     */
    public void stopMonsterVertically() {
        monster2.cancel();
    }

    /**
     * stop the monster from moving
     */
    public void stopMonsterHorizontally() {
        monster3.cancel();
    }

    /**
     * stop the monster from moving
     */
    public void stopMonsterFollowPlayer() {
        monster4.cancel();
    }

    /**
     * fill the ArrayList with monsters of different colors
     */
    private void fillImage() {
        Image ghost_1 = new Image(getClass().getResourceAsStream("icon/monster/ghost_1.png"));
        Image ghost_2 = new Image(getClass().getResourceAsStream("icon/monster/ghost_2.png"));
        Image ghost_3 = new Image(getClass().getResourceAsStream("icon/monster/ghost_3.png"));
        Image ghost_4 = new Image(getClass().getResourceAsStream("icon/monster/ghost_4.png"));
        ghostImages.add(ghost_1);
        ghostImages.add(ghost_2);
        ghostImages.add(ghost_3);
        ghostImages.add(ghost_4);
    }
}