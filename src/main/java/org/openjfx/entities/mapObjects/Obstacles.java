package org.openjfx.entities.mapObjects;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.openjfx.config.Settings;
import org.openjfx.style.GameElements;


public enum Obstacles {
    BoundaryTile("x", new Image(Settings.getImageUrl("boundary_tree")), "", true),
    EmptyTile("o", Color.rgb(27, 101, 92), "", false),
    EnemyTile("b", Color.rgb(27, 101, 92), "", false),
    SpawnTile("s", Color.rgb(70, 70, 70), "", false),
    DoorTile("d", Color.rgb(255, 224, 104), "Door", false),
    ExitTile("e", Color.rgb(255, 224, 104), "EXIT", false),
    TargetTile("t", Color.rgb(255, 23, 49), "", false),
    LockedTile("l", Color.rgb(255, 177, 123), "Lock", false),
    RangeTile("r", Color.rgb(97, 168, 130), "", false);


    private final String symbol;
    private final Color color;
    private final Image image;
    private final String text;
    private final boolean hasCollision;


    Obstacles(String symbol, Color color, String text, boolean hasCollision) {
        this.symbol = symbol;
        this.color = color;
        this.text = text;
        this.image = null;
        this.hasCollision = hasCollision;
    }
    Obstacles(String symbol, Image image, String text, boolean hasCollision) {
        this.symbol = symbol;
        this.image = image;
        this.text = text;
        this.color = null;
        this.hasCollision = hasCollision;
    }
    public String getSymbol() {
        return symbol;
    }

    private StackPane getImageShape(int width, int height) {
        ImageView image = new ImageView(this.image);
        image.setFitWidth(width);
        image.setFitHeight(height);

        Text text = new GameElements.BasicText(this.text);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(image, text);
        return stackPane;
    }
    private StackPane getColoredShape(int width, int height) {
        Rectangle shape = new Rectangle(width, height);
        shape.setFill(color);
        shape.setArcHeight(10);
        shape.setArcWidth(10);

        Text text = new GameElements.BasicText(this.text);
        return new StackPane(shape, text);
    }
    public StackPane getShape(int width, int height) {
        if (this.color == null) {
            return getImageShape(width, height);
        } else {
            return getColoredShape(width, height);
        }

    }
    public Node getShape() {
        return getShape(40, 40);
    }
    public boolean getHasCollision() {
        return hasCollision;
    }

}
