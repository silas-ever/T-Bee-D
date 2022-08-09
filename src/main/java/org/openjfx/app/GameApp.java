package org.openjfx.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.config.Settings;
import org.openjfx.controllers.SoundController;
import org.openjfx.scenes.WelcomeScene;

public class GameApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {

        SoundController.setSound(false);
        SoundController.startMenuSound();

        stage = primaryStage;

        primaryStage.setHeight(Settings.SCREEN_HEIGHT);
        primaryStage.setWidth(Settings.SCREEN_WIDTH);
        primaryStage.setTitle(Settings.APP_NAME);
        primaryStage.setScene(WelcomeScene.getWelcomeScreen());

        primaryStage.show();
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
