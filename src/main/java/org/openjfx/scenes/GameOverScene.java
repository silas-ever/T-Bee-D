package org.openjfx.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.openjfx.app.GameApp;
import org.openjfx.config.Settings;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.characterObjects.Weapons;
import org.openjfx.style.GameElements;

import java.io.IOException;

/**
 * JavaFX GameOverScene Class that displays T-Bee-D Game Over Screen
 *
 * @version 1.0
 */

public class GameOverScene {
    public static Scene getLossScreen(PlayerCharacter player) {

        // Setting up the main vbox at the center
        VBox vBox  = new VBox(25);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #61A882");

        // Setting up a vbox for the end screen game statistics
        VBox finalStats = new VBox();
        finalStats.setAlignment(Pos.CENTER);
        finalStats.setStyle("-fx-background-color: #61A882");

        // Making a text box for each of the statistics
        Text stat1 = new GameElements.BasicText("Total Monsters Killed: "
                + player.getTotalMonstersKilled());
        Text stat2 = new GameElements.BasicText("Total Damage Dealt: "
                + player.getTotalDamageDealt());
        Text stat3 = new GameElements.BasicText("Total Steps Taken: "
                + player.getTotalStepsTaken());

        // Adding the stat text to the final stats vbox
        finalStats.getChildren().add(stat1);
        finalStats.getChildren().add(stat2);
        finalStats.getChildren().add(stat3);

        // Setting up the hbox for weapon images at the center
        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);

        // Creating space between top of stage and game over message & adding them to the main vbox
        Region space = new Region();
        vBox.getChildren().add(space);
        vBox.getChildren().add(new GameElements.TitleText("GAME OVER :("));
        vBox.getChildren().add(finalStats);

        // Creating space between game over message & play again button
        for (int i = 0; i < 8; i++) {
            vBox.getChildren().add(new Region());
        }

        // Adding a player with honey image to the hbox
        PlayerCharacter displayCharacter = new PlayerCharacter("display", Weapons.Honey);
        ImageView playerHoney = displayCharacter.getCharacterImage(80);
        hBox.getChildren().add(playerHoney);

        // Adding a player with sword image to the hbox
        displayCharacter.changeWeapon(Weapons.Sword);
        ImageView playerSword = displayCharacter.getCharacterImage(80);
        hBox.getChildren().add(playerSword);

        // Adding a player with sword image to the hbox
        displayCharacter.changeWeapon(Weapons.Blueberry);
        ImageView playerBlueberry = displayCharacter.getCharacterImage(80);
        hBox.getChildren().add(playerBlueberry);

        // Adding the weapons hbox to the main vbox
        vBox.getChildren().add(hBox);

        // Adding a play again button to the main vbox
        GameElements.StandardButton playAgainButton =
                new GameElements.StandardButton("PLAY AGAIN?");
        playAgainButton.setOnAction(event -> {
            ConfigScene configScene = new ConfigScene();
            try {
                GameApp.setScene(configScene.getConfigScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        vBox.getChildren().add(playAgainButton);

        return new Scene(vBox, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }
}
