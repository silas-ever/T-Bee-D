package org.openjfx;

import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.controllers.RoomController;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.characterObjects.Weapons;
import org.openjfx.scenes.GameScene;
import org.testfx.framework.junit5.ApplicationTest;

public class PlayerMovementTests extends ApplicationTest {

    private final PlayerCharacter player = new PlayerCharacter("Name", Weapons.Blueberry);
    private RoomController curRoom;

    @Override
    public void start(Stage primaryStage) {
        GameScene newGame = new GameScene();
        primaryStage.setScene(newGame.getStartingGameScreen(player));
        curRoom = GameScene.getCurRoom();
        primaryStage.show();
    }

    @Test
    public void testPlayerStartingPositionX() {
        System.out.println(player.getCoordinate().getX());
        System.out.println(curRoom.getStartingCoordinate().getX());

        Assertions.assertEquals(curRoom.getStartingCoordinate().getX(),
                player.getCoordinate().getX());
    }

    @Test
    public void testPlayerStartingPositionY() {
        System.out.println(player.getCoordinate().getY());
        System.out.println(curRoom.getStartingCoordinate().getY());

        Assertions.assertEquals(curRoom.getStartingCoordinate().getY(),
                player.getCoordinate().getY());
    }
}
