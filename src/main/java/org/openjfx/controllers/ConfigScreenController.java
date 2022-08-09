package org.openjfx.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.entities.characterObjects.Weapons;
import org.openjfx.scenes.GameScene;
import org.openjfx.app.GameApp;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.config.Settings;
import org.openjfx.style.GameElements;

public class ConfigScreenController {
    @FXML private TextField name;
    @FXML private GridPane keyboard;
    @FXML private Text weaponName;
    @FXML private Slider difficulty;

    @FXML
    private void mouseEntered(MouseEvent mouseEvent) {
        Button btn = (Button) mouseEvent.getSource();
        btn.setStyle("-fx-background-color: #FFB17B; "
                + "-fx-background-radius: 10;");
    }

    @FXML
    private void mouseExited(MouseEvent mouseEvent) {
        Button btn = (Button) mouseEvent.getSource();
        btn.setStyle("-fx-background-color: #FFE068; "
                + "-fx-background-radius: 10;");
    }

    @FXML
    private void typeCharacter(ActionEvent event) {
        event.consume();
        Button key = (Button) event.getSource();
        name.appendText(key.getText());
    }

    @FXML
    private void changeCase(ActionEvent event) {
        event.consume();
        Button shift = (Button) event.getSource();
        ObservableList<Node> list = keyboard.getChildren();
        for (Node node:list) {
            if (((Button) node).getText().length() == 1) {
                if (shift.getText().equals("lower")) {
                    ((Button) node).setText(((Button) node).getText().toLowerCase());
                } else if (shift.getText().equals("UPPER")) {
                    ((Button) node).setText(((Button) node).getText().toUpperCase());
                }
            }
        }
        if (shift.getText().equals("lower")) {
            shift.setText("UPPER");
        } else if (shift.getText().equals("UPPER")) {
            shift.setText("lower");
        }
    }

    @FXML
    private void space(ActionEvent event) {
        event.consume();
        name.appendText(" ");
    }

    @FXML
    private void backspace(ActionEvent event) {
        event.consume();
        if (name.getText() != null && name.getText().length() > 0) {
            name.setText(name.getText().substring(0, name.getText().length() - 1));
        }
    }

    @FXML
    private void setWeapon(ActionEvent event) {
        event.consume();
        Button weaponChoice = (Button) event.getSource();
        weaponName.setText(weaponChoice.getAccessibleText());
    }

    @FXML
    private void startGame(ActionEvent event) {
        event.consume();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox();
        dialogVbox.setStyle("-fx-font-family: Futura; -fx-font-weight: BOLD; -fx-font-size:  14;"
                + "-fx-background-color: #61A882; -fx-text-fill: rgb(27, 101, 92)");
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setSpacing(20);
        Text error = new Text();
        error.setFont(Font.font(24));
        error.setWrappingWidth(250);
        error.setTextAlignment(TextAlignment.CENTER);
        Button ok = new GameElements.StandardButton("OK");
        ok.setOnAction(event1 -> dialog.close());

        if (name.getText().trim().isEmpty() && weaponName.getText().equals("No weapon")) {
            error.setText("You must enter a name and choose a weapon");
            dialogVbox.getChildren().add(error);
            dialogVbox.getChildren().add(ok);
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        } else if (name.getText().trim().isEmpty()) {
            error.setText("You must enter a name");
            dialogVbox.getChildren().add(error);
            dialogVbox.getChildren().add(ok);
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        } else if (weaponName.getText().equals("No weapon")) {
            error.setText("You must choose a weapon");
            dialogVbox.getChildren().add(error);
            dialogVbox.getChildren().add(ok);
            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        } else {
            Settings.setDifficulty(difficulty.getValue());
            GameScene gameScene = new GameScene();

            GameApp.setScene(gameScene.getStartingGameScreen(new PlayerCharacter(name.getText(),
                    getStartingWeaponFromText(weaponName.getText()))));
        }
    }
    private Weapons getStartingWeaponFromText(String text) {
        for (Weapons startingWeapon: Weapons.values()) {
            if (startingWeapon.getWeaponName().equals(text)) {
                return startingWeapon;
            }
        }
        return null;
    }
}
