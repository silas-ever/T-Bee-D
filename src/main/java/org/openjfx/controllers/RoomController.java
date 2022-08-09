package org.openjfx.controllers;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.openjfx.app.GameApp;
import org.openjfx.config.Settings;
import org.openjfx.entities.Coordinate;
import org.openjfx.entities.characterObjects.Enemy;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.mapObjects.Door;
import org.openjfx.entities.mapObjects.MapGenerator;
import org.openjfx.entities.mapObjects.Tile;
import org.openjfx.scenes.GameOverScene;
import org.openjfx.scenes.GameScene;
import org.openjfx.scenes.WinScene;
import org.openjfx.style.GameElements;

import static org.openjfx.entities.mapObjects.Obstacles.*;

public class RoomController {
    //region Constants
    private static final int GAP_SIZE = 3;
    private static final int MIN_TOP_PANE_HEIGHT = 60;
    private static final int TILE_SIZE = 40;
    private static final int GRID_SIZE = TILE_SIZE + GAP_SIZE;
    //endregion

    private final Coordinate startingCoordinate;

    //region Character Variables
    private final PlayerCharacter playerCharacter;
    private final Group characterView;
    //endregion

    //region Movement Variables
    private boolean goUp;
    private boolean goDown;
    private boolean goRight;
    private boolean goLeft;
    private boolean keyChanged = false;
    private boolean holdPress = false;
    //endregion

    private boolean isAttackMode = false;
    private final Tile tile;

    private Text healthText;
    private Text locationText;
    //region Constructors
    /**
     * This is the constructor that is called at the start of the game.
     * This will default to the starting map.
     * @param playerCharacter the main character.
     */
    public RoomController(PlayerCharacter playerCharacter) {
        tile = MapGenerator.startMap(new Coordinate(9, 9),
                new Coordinate(5, 1),
                new Coordinate(5, 8));
        startingCoordinate = tile.getSpawn();
        this.playerCharacter = playerCharacter;
        this.playerCharacter.setCoordinate(startingCoordinate);

        characterView = playerCharacter.getCharacterView();
    }

    /**
     * This is the constructor that is called during the game.
     * It will run whatever tile is entered into it and place
     * the character at the coordinates indicated.
     * @param doorEntered the door the player entered from.
     * @param playerCharacter main character
     */
    public RoomController(Door doorEntered, PlayerCharacter playerCharacter) {
        this.tile = doorEntered.getTile();
        startingCoordinate = doorEntered.getPlayerSpawn();
        this.playerCharacter = playerCharacter;
        this.playerCharacter.setCoordinate(startingCoordinate);
        System.out.println(this.playerCharacter.getCoordinate());

        characterView = playerCharacter.getCharacterView();
    }
    //endregion

    private int getInitialPlayerX() {
        return getBoundaryStartX() + (startingCoordinate.getX() * GRID_SIZE);
    }

    private int getInitialPlayerY() {
        return getBoundaryStartY()
                + ((startingCoordinate.getY()) * GRID_SIZE)
                + MIN_TOP_PANE_HEIGHT;
    }

    private Coordinate getPlayerTrueCoordinate(Coordinate coordinate) {
        return new Coordinate(getBoundaryStartX() + (coordinate.getX() * GRID_SIZE),
                getBoundaryStartY() + (coordinate.getY() * GRID_SIZE) + MIN_TOP_PANE_HEIGHT);
    }

    //region Window Formatting Code
    private int getBoundaryStartX() {
        return (Settings.SCREEN_WIDTH - (tile.getMaxX() * GRID_SIZE)) / 2;
    }
    private int getBoundaryStartY() {
        int difference = Settings.SCREEN_HEIGHT - (tile.getMaxY() * GRID_SIZE);
        if (difference > (2 * MIN_TOP_PANE_HEIGHT)) {
            return difference / 2;
        }
        return MIN_TOP_PANE_HEIGHT;
    }
    private Node getTopPane(PlayerCharacter playerCharacter) {
        Text nameText = new GameElements.BasicText("NAME: " + playerCharacter.getCharacterName());
        Text moneyText = new GameElements.BasicText("MONEY: " + playerCharacter.getMoney());
        locationText = new GameElements.BasicText(tile.getCoordinate().toString());
        healthText = new GameElements.BasicText("HEALTH: "
                + playerCharacter.getHealth());
        Text spacer = new GameElements.BasicText("");

        VBox pane = new VBox(nameText, moneyText, locationText);
        VBox healthPane = new VBox(spacer, healthText);
        pane.setMinHeight(MIN_TOP_PANE_HEIGHT);

        BorderPane topPane = new BorderPane();
        topPane.setRight(pane);
        topPane.setLeft(healthPane);

        topPane.setMaxWidth(Settings.SCREEN_WIDTH - 40);
        return topPane;
    }
    //endregion

    //updating stats in the top pane
    public void updateHealthText() {
        healthText.setText("HEALTH: " + playerCharacter.getHealth());
    }
    public void updateLocationText() {
        locationText.setText(playerCharacter.getCoordinate().toString());
    }
    //end updating stats

    public Scene getGameScene() {
        VBox layout = new VBox();
        layout.getChildren().addAll(getTopPane(playerCharacter),
                tile.getTile());
        layout.setAlignment(Pos.CENTER);

        layout.setLayoutX(getBoundaryStartX());
        layout.setLayoutY(getBoundaryStartY());
        characterView.relocate(getInitialPlayerX(), getInitialPlayerY());

        Group root = new Group(layout, characterView);

        for (Enemy enemy:tile.getEnemies()) {
            Coordinate trueCoordinate = getPlayerTrueCoordinate(enemy.getCoordinate());
            Group enemyView = enemy.getCharacterView();
            enemyView.relocate(trueCoordinate.getX(), trueCoordinate.getY());
            root.getChildren().add(enemyView);
            System.out.println(enemy);
        }

        Scene scene = new Scene(root, Settings.SCREEN_WIDTH,
                Settings.SCREEN_HEIGHT, Color.rgb(97, 168, 130));

        scene.setOnKeyPressed(keyPress -> {
            if (keyPress.getCode() == KeyCode.Z) {
                for (Enemy enemy:tile.getEnemies()) {
                    if (!enemy.isFainted()) {
                        int eX = enemy.getCoordinate().getX();
                        int eY = enemy.getCoordinate().getY();
                        checkValidEnemies(eX, eY, enemy);
                    } else {
                        tile.getEnemies().remove(enemy);
                    }
                }
            }
            switch (keyPress.getCode()) {
            case LEFT:
                goLeft = true;
                break;
            case RIGHT:
                goRight = true;
                break;
            case UP:
                goUp = true;
                break;
            case DOWN:
                goDown = true;
                break;
            default:
                break;
            }
            if (!holdPress) {
                holdPress = true;
                unSetAllMoveButtons();
            } else {
                keyChanged = true;
                holdPress = false;
            }
        });

        // Resets boolean after key is released
        scene.setOnKeyReleased(keyReleased -> {
            switch (keyReleased.getCode()) {
            case LEFT:
                goLeft = true;
                break;
            case RIGHT:
                goRight = true;
                break;
            case UP:
                goUp = true;
                break;
            case DOWN:
                goDown = true;
                break;
            default:
                break;
            }
            keyChanged = true;
            holdPress = false;
        });

        /*
        Checks keys periodically and updates change in x
        or change in y for player movement
        */
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long time) {
                int dx = 0;
                int dy = 0;
                if (!isAttackMode) {
                    if (keyChanged) {
                        if (goLeft) {
                            dx -= GRID_SIZE;
                        } else if (goRight) {
                            dx += GRID_SIZE;
                        } else if (goUp) {
                            dy -= GRID_SIZE;
                        } else if (goDown) {
                            dy += GRID_SIZE;
                        }
                        unSetAllMoveButtons();
                        updatePositionBy(dx, dy, characterView);
                    }
                }
                keyChanged = false;
            }
        };
        timer.start();

        for (Enemy enemy : tile.getEnemies()) {
            enemy.startConstantAnimation();
        }
        playerCharacter.startConstantAnimation();

        if (playerCharacter.isFainted()) {
            GameApp.setScene(GameOverScene.getLossScreen(playerCharacter));
        }
        return scene;
    }

    private void checkValidEnemies(int eX, int eY, Enemy enemy) {
        int pX = playerCharacter.getCoordinate().getX();
        int pY = playerCharacter.getCoordinate().getY();

        //left & right
        if (Math.abs(eX - pX) <= playerCharacter.getAttackRange() && eY == pY) {

            //animation
            playerCharacter.stopConstantAnimation();
            playerCharacter.startAttackingAnimation();
            enemy.stopConstantAnimation();
            enemy.startAttackingAnimation();
            //end animation

            enemy.getCharacterImageFrame(4).setPickOnBounds(true);
            enemy.getCharacterImageFrame(4).setOnMouseClicked(event -> {
                //attack
                isAttackMode = false;
                tile.getTile().add(EnemyTile.getShape(), eX, eY);
                playerCharacter.attack(enemy);
                playerCharacter.regenHealth();
                System.out.println("Enemy health: " + enemy.getHealth());
                updateHealthText();
                if (enemy.isFainted()) {
                    tile.getEnemies().remove(enemy);
                }
            });

            Node target = TargetTile.getShape();
            tile.getTile().add(target, eX, eY);
            isAttackMode = true;
        } else if (eX == pX && Math.abs(eY - pY) // up & down
                <= playerCharacter.getAttackRange()) {

            //animation
            playerCharacter.stopConstantAnimation();
            playerCharacter.startAttackingAnimation();
            enemy.stopConstantAnimation();
            enemy.startAttackingAnimation();
            //end animation

            enemy.getCharacterImageFrame(4).setPickOnBounds(true);
            enemy.getCharacterImageFrame(4).setOnMouseClicked(event -> {
                //attack
                isAttackMode = false;
                tile.getTile().add(EnemyTile.getShape(), eX, eY);
                playerCharacter.attack(enemy);
                playerCharacter.regenHealth();
                System.out.println("Enemy health: " + enemy.getHealth());
                updateHealthText();
                if (enemy.isFainted()) {
                    tile.getEnemies().remove(enemy);
                }
            });

            Node target = TargetTile.getShape();
            tile.getTile().add(target, eX, eY);
            isAttackMode = true;
        }
    }

    private void checkEnemyAttack(Enemy enemy) {
        if (!enemy.isFainted()) {
            int pX = playerCharacter.getCoordinate().getX();
            int pY = playerCharacter.getCoordinate().getY();
            //left & right
            if (Math.abs(enemy.getCoordinate().getX() - pX) <= enemy.getAttackRange()
                    && enemy.getCoordinate().getY() == pY) {
                enemy.attack(playerCharacter);
                System.out.println("Player health: " + playerCharacter.getHealth());
                updateHealthText();
            } else if (enemy.getCoordinate().getX() == pX // up & down
                    && Math.abs(enemy.getCoordinate().getY() - pY) <= enemy.getAttackRange()) {
                enemy.attack(playerCharacter);
                System.out.println("Player health: " + playerCharacter.getHealth());
                updateHealthText();
            }
        }
    }

    private void unSetAllMoveButtons() {
        goUp = false;
        goRight = false;
        goDown = false;
        goLeft = false;
    }

    //region Player Movement
    public void movePlayer(double x, double y, Group playerView) {
        double changeX = playerView.getBoundsInLocal().getWidth() / 2;
        double changeY = playerView.getBoundsInLocal().getHeight() / 2;

        double nx = x - getBoundaryStartX();
        double ny = y - getBoundaryStartY() - GRID_SIZE;
        nx /= GRID_SIZE;
        ny /= GRID_SIZE;
        Coordinate newCoordinate = new Coordinate((int) nx, (int) ny);

        if (tile.isInBounds(newCoordinate)) {
            if (tile.isDoor(newCoordinate)) {
                Door door = tile.getDoor(newCoordinate);
                if (isValidExit(door)) {
                    if (door.isExit()) {
                        System.out.println("EXIT");
                        SoundController.victorySound();
                        GameApp.setScene(WinScene.getWinScreen(playerCharacter));
                    } else {
                        System.out.println(door);
                        SoundController.doorSound();
                        GameScene.changeScene(MapGenerator.getNextDoor(door));
                    }
                } else {
                    playLockedDoorAnimation(newCoordinate);
                }
            } else {
                SoundController.moveSound();
                playerCharacter.setCoordinate(newCoordinate);
                System.out.println(playerCharacter.getCoordinate());

                playerView.relocate(x - changeX, y - changeY);
                // enemy attack
                if (!tile.getEnemies().isEmpty() && !playerCharacter.isFainted()) {
                    for (Enemy enemy : tile.getEnemies()) {
                        checkEnemyAttack(enemy);
                        if (playerCharacter.isFainted()) {
                            GameApp.setScene(GameOverScene.getLossScreen(playerCharacter));
                        }
                    }
                }
            }
            int newStepsTaken = playerCharacter.getTotalStepsTaken() + 1;
            playerCharacter.setTotalStepsTaken(newStepsTaken);
        }
    }
    private void updatePositionBy(int dx, int dy, Group playerView) {
        if ((dx == 0) && (dy == 0)) {
            return;
        }

        double changeX = playerView.getBoundsInLocal().getWidth() / 2;
        double changeY = playerView.getBoundsInLocal().getHeight() / 2;

        double x = playerView.getLayoutX() + changeX + dx;
        double y = playerView.getLayoutY() + changeY + dy;

        movePlayer(x, y, playerView);
    }
    //endregion

    public Coordinate getStartingCoordinate() {
        return startingCoordinate.clone();
    }

    private boolean isValidExit(Door door) {
        if (tile.getEnemies().size() > 0) {
            return door.getConnectedDoor() != null;
        }
        return true;
    }

    private void playLockedDoorAnimation(Coordinate coordinate) {

        Node lockedDoor = LockedTile.getShape();
        tile.getTile().add(lockedDoor, coordinate.getX(), coordinate.getY());

        Timeline lockedAnimation = new Timeline();
        lockedAnimation.setCycleCount(10);
        lockedAnimation.getKeyFrames().add(new KeyFrame(
            Duration.millis(1000),
            (ActionEvent event) -> lockedDoor.setOpacity(0)
        ));

        lockedAnimation.play();
    }
}
