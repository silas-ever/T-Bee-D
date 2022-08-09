package org.openjfx.entities.characterObjects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.openjfx.config.Settings;
import org.openjfx.entities.Coordinate;

public abstract class Character {
    private final String characterName;
    private String characterImageUrl;
    private final int size;
    private Coordinate coordinate;

    //all frames
    private final ImageView[] characterImages;
    private Group characterView;

    //animation
    private Timeline constantAnimation;
    private Timeline attackedAnimation;
    private Timeline attackingAnimation;
    private Timeline faintedAnimation;
    //end animation

    private int health;
    private int attackDamage;
    private int attackRange;
    private boolean fainted;

    public Character(String characterName, String characterImageUrl, int size) {
        this.characterName = characterName;
        this.characterImageUrl = characterImageUrl.toLowerCase();
        this.size = size;
        this.fainted = false;
        characterImages = new ImageView[4];
    }

    public String getCharacterName() {
        return characterName;
    }

    public ImageView getCharacterImage() {
        if (characterImages[0] == null) {
            setNewImage(0);
        }
        return characterImages[0];
    }

    public ImageView getCharacterImageFrame(int frame) {
        if (characterImages[frame - 1] == null) {
            setNewImage(frame - 1);
        }

        return characterImages[frame - 1];
    }

    public ImageView getCharacterImage(int size) {
        ImageView image = new ImageView(
                new Image(
                        Settings.getImageUrl(characterImageUrl + "01")));
        image.setFitHeight(size);
        image.setFitWidth(size);
        return image;
    }

    public void setNewImage(int index) {
        characterImages[index] = new ImageView(
                new Image(
                        Settings.getImageUrl(characterImageUrl + "0" + (index + 1))));
        characterImages[index].setFitHeight(size);
        characterImages[index].setFitWidth(size);
    }

    public Group getCharacterView() {
        if (characterView == null) {
            Group player = new Group(getCharacterImageFrame(2));

            //constant animation
            constantAnimation = new Timeline();
            constantAnimation.setCycleCount(Timeline.INDEFINITE);
            constantAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(200),
                (ActionEvent event) -> player.getChildren().setAll(getCharacterImageFrame(2))
            ));
            constantAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(400),
                (ActionEvent event) -> player.getChildren().setAll(getCharacterImageFrame(1))
            ));
            //end constant animation

            //being attacked animation
            attackedAnimation = new Timeline();
            attackedAnimation.setCycleCount(7);
            int speed = 25;
            attackedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(speed),
                (ActionEvent event) -> player.setOpacity(.75)
            ));
            attackedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * speed),
                (ActionEvent event) -> player.setOpacity(.5)
            ));
            attackedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * speed),
                (ActionEvent event) -> player.setOpacity(.25)
            ));
            attackedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(4 * speed),
                (ActionEvent event) -> player.setOpacity(.5)
            ));
            attackedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(5 * speed),
                (ActionEvent event) -> player.setOpacity(.75)
            ));
            attackedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(6 * speed),
                (ActionEvent event) -> player.setOpacity(1)
            ));
            //end being attacked animation

            //attacking animation
            attackingAnimation = new Timeline();
            attackingAnimation.setCycleCount(Timeline.INDEFINITE);

            attackingAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(200),
                (ActionEvent event) -> {
                    //case if player has been attacked and attacked back right away
                    stopAttackedAnimation();
                    player.setOpacity(1);

                    player.getChildren().setAll(getCharacterImageFrame(4));
                }
            ));
            //end attacking animation

            //fainted animation
            faintedAnimation = new Timeline();
            faintedAnimation.setCycleCount(10);

            faintedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(speed),
                (ActionEvent event) -> player.getChildren().setAll(getCharacterImageFrame(3))
            ));
            faintedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * speed),
                (ActionEvent event) -> player.setOpacity(.75)
            ));
            faintedAnimation.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * speed),
                (ActionEvent event) -> {
                    player.setOpacity(0);
                    this.stopAttackedAnimation();
                    this.stopConstantAnimation();
                }
            ));
            //end fainted animation

            characterView = player;
        }

        return characterView;
    }

    protected void setCharacterImageUrl(String characterImageUrl) {
        this.characterImageUrl = characterImageUrl;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (health <= 0) {
            setFainted(true);
        }
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public boolean isFainted() {
        return this.fainted;
    }

    public void setFainted(boolean fainted) {
        this.fainted = fainted;
    }

    @Override
    public String toString() {
        return "Character: " + characterName + " At: " + coordinate + " Of Size: " + size
                + "\n With Url: " + characterImageUrl;
    }

    public void startConstantAnimation() {
        constantAnimation.play();
    }

    public void stopConstantAnimation() {
        constantAnimation.stop();
    }

    public void startAttackedAnimation() {
        attackedAnimation.play();
    }

    public void stopAttackedAnimation() {
        attackedAnimation.stop();
    }

    public void startAttackingAnimation() {
        attackingAnimation.play();
    }

    public void stopAttackingAnimation() {
        attackingAnimation.stop();
    }

    public void startFaintedAnimation() {
        faintedAnimation.play();
    }

    public void stopFaintedAnimation() {
        faintedAnimation.stop();
    }
}
