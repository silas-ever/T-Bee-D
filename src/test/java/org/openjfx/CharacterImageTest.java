package org.openjfx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.config.Settings;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.characterObjects.Weapons;
import org.testfx.framework.junit5.ApplicationTest;

public class CharacterImageTest extends ApplicationTest {

    @Test
    public void testCharacterWithHoneyWeapon() {
        PlayerCharacter player = new PlayerCharacter("character",
                Weapons.Honey);
        Assertions.assertEquals(player.getCharacterImage().getImage().getUrl(),
                Settings.getImageUrl("player_bee_honey01"));
    }

    @Test
    public void testCharacterWithSwordWeapon() {
        PlayerCharacter player = new PlayerCharacter("character",
                Weapons.Sword);
        Assertions.assertEquals(player.getCharacterImage().getImage().getUrl(),
                Settings.getImageUrl("player_bee_sword01"));
    }

    @Test
    public void testCharacterWithBlueberryWeapon() {
        PlayerCharacter player = new PlayerCharacter("character",
                Weapons.Blueberry);
        Assertions.assertEquals(player.getCharacterImage().getImage().getUrl(),
                Settings.getImageUrl("player_bee_blueberry01"));
    }
}
