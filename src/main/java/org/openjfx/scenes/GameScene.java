package org.openjfx.scenes;

import javafx.scene.Scene;
import org.openjfx.app.GameApp;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.mapObjects.Door;
import org.openjfx.controllers.RoomController;

public class GameScene {
    private static PlayerCharacter playerCharacter;
    private static RoomController curRoom;
    public Scene getStartingGameScreen(PlayerCharacter playerCharacter) {
        GameScene.playerCharacter = playerCharacter;
        curRoom = new RoomController(playerCharacter);
        return curRoom.getGameScene();
    }

    public static void changeScene(Door newDoor) {
        curRoom = new RoomController(newDoor, playerCharacter);
        GameApp.setScene(curRoom.getGameScene());
    }

    public static RoomController getCurRoom() {
        return curRoom;
    }
}