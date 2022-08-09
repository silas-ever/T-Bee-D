package org.openjfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.config.Settings;
import org.openjfx.entities.characterObjects.Weapons;
import org.testfx.framework.junit5.ApplicationTest;


import java.util.HashSet;

public class InitializationTests extends ApplicationTest {

    @Test
    public void difficultyChangesStartingMoney() {
        HashSet<Integer> startingMoneyValues = new HashSet<>();
        System.out.println("Starting Test difficultyChangesStartingMoney()"
                + "\n=============================");
        for (int i = 0; i <= 10; i++) {
            Settings.setDifficulty(i);
            PlayerCharacter testCharacter = new PlayerCharacter("Test");
            System.out.println("Difficulty: " + Settings.getDifficulty()
                    + "\nCharacter Money: " + testCharacter.getMoney());
            Assertions.assertFalse(startingMoneyValues.contains(testCharacter.getMoney()));
            startingMoneyValues.add(testCharacter.getMoney());
        }
    }

    @Test
    public void differentStartingWeaponsHaveDifferentWeaponImageStrings() {
        HashSet<String> differentStartingWeaponImages = new HashSet<>();
        System.out.println("Starting Test "
                + "differentStartingWeaponsHaveDifferentWeaponImageStrings()"
                + "\n=============================");
        for (Weapons startingWeapon : Weapons.values()) {
            System.out.println("Weapon String: "
                    + startingWeapon.getWeaponImage().getImage().getUrl());
            Assertions.assertFalse(differentStartingWeaponImages.contains(
                    startingWeapon.getWeaponImage().getImage().getUrl()));
            differentStartingWeaponImages.add(
                    startingWeapon.getWeaponImage().getImage().getUrl());
        }
    }
}
