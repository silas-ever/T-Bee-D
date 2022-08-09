package org.openjfx.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.openjfx.app.GameApp;
import org.openjfx.config.Settings;
import org.openjfx.entities.mapObjects.Obstacles;
import org.openjfx.style.GameElements;

public class HowToPlayScene {
    private static Scene howToPlayScene;

    public static Scene getHowToPlayScreen() {
        if (howToPlayScene == null) {
            VBox vBox  = new VBox(25);
            vBox.setAlignment(Pos.CENTER);
            vBox.setStyle("-fx-background-color: #61A882");

            vBox.getChildren().add(new GameElements.TitleText("How To Play"));
            vBox.getChildren().add(new GameElements.BasicText(
                    "To attack an enemy press Z and click on an enemy with a mouse."));

            GridPane weaponTable = new GridPane();
            weaponTable.setVgap(20);
            weaponTable.setHgap(20);
            weaponTable.setAlignment(Pos.CENTER);
            //row 1
            weaponTable.add(new GameElements.TitleText("Weapons:"), 1, 1);
            weaponTable.add(getImageSized("weapon_sword"), 2, 1);
            weaponTable.add(getImageSized("weapon_honey"), 3, 1);
            weaponTable.add(getImageSized("weapon_blueberry"), 4, 1);

            //row 2
            weaponTable.add(new GameElements.BasicText("RANGE:"), 1, 2);
            weaponTable.add(new GameElements.BasicText("1"), 2, 2);
            weaponTable.add(new GameElements.BasicText("2"), 3, 2);
            weaponTable.add(new GameElements.BasicText("3"), 4, 2);

            //row 3
            HBox swordDemo = new HBox();
            swordDemo.setSpacing(3);
            swordDemo.getChildren().addAll(getPlayer("sword"), getEnemy("hornet"));

            HBox honeyDemo = new HBox();
            honeyDemo.setSpacing(3);
            honeyDemo.getChildren().addAll(getPlayer("honey"),
                    Obstacles.EmptyTile.getShape(), getEnemy("hornet"));

            HBox blueberryDemo = new HBox();
            blueberryDemo.setSpacing(3);
            blueberryDemo.getChildren().addAll(getPlayer("blueberry"),
                    Obstacles.EmptyTile.getShape(),
                    Obstacles.EmptyTile.getShape(),
                    getEnemy("hornet"));

            weaponTable.add(swordDemo, 2, 3);
            weaponTable.add(honeyDemo, 3, 3);
            weaponTable.add(blueberryDemo, 4, 3);

            //row 4
            weaponTable.add(new GameElements.TitleText("Enemies:"), 1, 4);
            weaponTable.add(getEnemiesStackPane("enemy_venus_fly_trap01"), 2, 4);
            weaponTable.add(getEnemiesStackPane("enemy_ant01"), 3, 4);
            weaponTable.add(getEnemiesStackPane("enemy_hornet01"), 4, 4);

            //row 5
            weaponTable.add(new GameElements.BasicText("HEALTH:"), 1, 5);
            weaponTable.add(new GameElements.BasicText("high"), 2, 5);
            weaponTable.add(new GameElements.BasicText("medium"), 3, 5);
            weaponTable.add(new GameElements.BasicText("low"), 4, 5);

            //row 6
            weaponTable.add(new GameElements.BasicText("STRENGTH:"), 1, 6);
            weaponTable.add(new GameElements.BasicText("low"), 2, 6);
            weaponTable.add(new GameElements.BasicText("medium"), 3, 6);
            weaponTable.add(new GameElements.BasicText("high"), 4, 6);

            //row 7
            weaponTable.add(new GameElements.BasicText("RANGE:"), 1, 7);
            weaponTable.add(new GameElements.BasicText("1"), 2, 7);
            weaponTable.add(new GameElements.BasicText("1"), 3, 7);
            weaponTable.add(new GameElements.BasicText("3"), 4, 7);

            //HBox weaponDemo = new HBox(,swordDemo, honeyDemo,blueberryDemo);

            vBox.getChildren().add(weaponTable);
            Button backBtn = new GameElements.StandardButton("BACK");
            backBtn.setOnAction(event -> GameApp.setScene(WelcomeScene.getWelcomeScreen()));
            vBox.getChildren().add(backBtn);

            howToPlayScene = new Scene(vBox, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        }

        return howToPlayScene;
    }

    private static ImageView getPlayerImageSized(String url) {
        ImageView image = new ImageView(new Image(Settings.getImageUrl(url)));
        image.setFitHeight(40);
        image.setFitWidth(40);

        return image;
    }

    private static ImageView getImageSized(String url) {
        ImageView image = new ImageView(new Image(Settings.getImageUrl(url)));
        image.setFitHeight(80);
        image.setFitWidth(80);

        return image;
    }

    private static StackPane getPlayer(String weapon) {
        StackPane pane = new StackPane();
        pane.getChildren().addAll(Obstacles.EmptyTile.getShape(),
                getPlayerImageSized("player_bee_" + weapon + "04"));
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private static StackPane getEnemy(String enemy) {
        StackPane pane = new StackPane();
        pane.getChildren().addAll(Obstacles.TargetTile.getShape(),
                getPlayerImageSized("enemy_" + enemy + "01"));
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private static StackPane getEnemiesStackPane(String enemy) {
        StackPane pane = new StackPane();
        StackPane tile = Obstacles.EnemyTile.getShape(80, 80);
        tile.setAlignment(Pos.CENTER_LEFT);

        pane.getChildren().addAll(tile, getImageSized(enemy));
        pane.setAlignment(Pos.CENTER_LEFT);
        return pane;
    }
}
