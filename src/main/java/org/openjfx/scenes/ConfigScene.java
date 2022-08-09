package org.openjfx.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.openjfx.config.Settings;
import java.io.IOException;
import java.util.Objects;

public class ConfigScene {
    public Scene getConfigScene() throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/ConfigScreen.fxml")));
      
        Scene configScene = new Scene(root, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        configScene.getStylesheets().add("file:src/main/resources/css/GameElementStyles.css");
        return configScene;
    }
}
