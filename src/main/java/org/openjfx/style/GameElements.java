package org.openjfx.style;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * org.openjfx.Styles.GameElements class that consists of all methods and classes necessary to
 * keep the style of the game consistent.
 * @version 1.0
 */
public class GameElements {
    /**
     * Method to get the Font object for titles.
     * @return title Font object.
     */
    public static Font getTitleFont() {
        return Font.font("Futura", FontWeight.BOLD, 20);
    }

    /**
     * Method to get the Font object for basic text.
     * @return basic Font object.
     */
    public static Font getBasicFont() {
        return Font.font("Futura", FontWeight.BOLD, 14);
    }

    /**
     * A class that creates title text and inherits from Text.
     */
    public static class TitleText extends Text {
        /**
         * Constructor that sets the text to the new object.
         * @param text text.
         */
        public TitleText(String text) {
            super(text);
            this.setFont(getTitleFont());
        }
    }

    /**
     * A class that creates basic text and inherits from Text.
     */
    public static class BasicText extends Text {
        /**
         * Constructor that sets the text to the new object.
         * @param text text.
         */
        public BasicText(String text) {
            super(text);
            this.setFont(getBasicFont());
            this.setFill(Color.rgb(27, 101, 92));
        }
    }

    /**
     * A class that creates a new styled button. Inherits from Button.
     */
    public static class StandardButton extends Button {
        /**
         * Constructor that created a styled button.
         * @param text text.
         */
        public StandardButton(String text) {
            super(text);

            this.setFont(getTitleFont());
            this.setStyle("-fx-background-color: #FFE068; "
                    + "-fx-background-radius: 10;");
            this.setPrefHeight(40);


            this.setOnMouseEntered(mouseEvent -> {
                StandardButton btn =
                        (StandardButton) mouseEvent.getSource();
                btn.setStyle("-fx-background-color: #FFB17B; "
                        + "-fx-background-radius: 10;");
            });

            this.setOnMouseExited(mouseEvent -> {
                StandardButton btn =
                        (StandardButton) mouseEvent.getSource();
                btn.setStyle("-fx-background-color: #FFE068; "
                        + "-fx-background-radius: 10;");
            });
        }
    }
}