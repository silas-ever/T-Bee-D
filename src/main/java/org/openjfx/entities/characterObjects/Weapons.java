package org.openjfx.entities.characterObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.openjfx.config.Settings;

public enum Weapons {
    Blueberry("Blueberry", 15, 3),
    Honey("Honey", 20, 2),
    Sword("Sword", 30, 1);

    private final String weaponName;
    private final int damage;
    private final int range;

    Weapons(String weaponName, int damage, int range) {
        this.weaponName = weaponName;
        this.damage = damage;
        this.range = range;
    }

    public ImageView getWeaponImage(int size) {
        ImageView weaponImageview =
                new ImageView(
                        new Image(Settings.getImageUrl("weapon_" + weaponName.toLowerCase())));
        weaponImageview.setFitHeight(size);
        weaponImageview.setFitWidth(size);

        return weaponImageview;
    }

    public ImageView getWeaponImage() {
        return getWeaponImage(100);
    }
    public String getWeaponName() {
        return weaponName;
    }
    public int getDamage() {
        return damage;
    }
    public int getRange() {
        return range;
    }
}
