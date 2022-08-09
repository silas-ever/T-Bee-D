package org.openjfx.scenes;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.openjfx.app.GameApp;
import org.openjfx.config.Settings;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.characterObjects.Weapons;
import org.openjfx.style.GameElements;
import javafx.scene.image.Image;

import java.io.IOException;

/**
 * JavaFX WelcomeScene Class that displays T-Bee-D Game Welcome Screen
 *
 * @version 1.0
 */

public class WelcomeScene {
    public static Scene getWelcomeScreen() {

        // setting up a vbox at the top center
        VBox vBox  = new VBox(25);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #61A882");

        // setting up an hbox at the center
        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);

        //logo animation
        ImageView logo1 = new ImageView(new Image(Settings.getImageUrl("T-Bee-D01")));

        logo1.setFitWidth(750);
        logo1.setFitHeight(510);

        Group logo = new Group(logo1);
        logo.relocate(Settings.SCREEN_WIDTH / 2 - 375, 50);
        vBox.getChildren().add(logo);

        // adding a player with honey image to the hbox
        PlayerCharacter displayCharacter = new PlayerCharacter("display", Weapons.Honey);
        ImageView playerHoney = displayCharacter.getCharacterImage(80);
        hBox.getChildren().add(playerHoney);

        // adding a player with sword image to the hbox
        displayCharacter.changeWeapon(Weapons.Sword);
        ImageView playerSword = displayCharacter.getCharacterImage(80);
        hBox.getChildren().add(playerSword);

        // adding a player with sword image to the hbox
        displayCharacter.changeWeapon(Weapons.Blueberry);
        ImageView playerBlueberry = displayCharacter.getCharacterImage(80);
        hBox.getChildren().add(playerBlueberry);

        // adding the hbox to the vbox
        vBox.getChildren().add(hBox);

        // adding a start button to the vbox
        GameElements.StandardButton startButton = new GameElements.StandardButton("START");
        startButton.setId("button");
        startButton.setOnAction(event -> {
            ConfigScene configScene = new ConfigScene();
            try {
                GameApp.setScene(configScene.getConfigScene());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        GameElements.StandardButton howToPlayButton =
                new GameElements.StandardButton("HOW TO PLAY");
        howToPlayButton.setOnAction(event -> {
            GameApp.setScene(HowToPlayScene.getHowToPlayScreen());
        });

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(startButton, howToPlayButton);

        vBox.getChildren().add(buttons);

        vBox.setMinWidth(Settings.SCREEN_WIDTH);
        vBox.setMinHeight(Settings.SCREEN_HEIGHT);

        return new Scene(vBox, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }
}


