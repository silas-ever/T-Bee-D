package org.openjfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.entities.characterObjects.PlayerCharacter;

public class InitialStartupTests {
    private final PlayerCharacter player = new PlayerCharacter("Player 1");

    @Test
    public void nameNotEmptyString() {
        Assertions.assertNotEquals(player.getCharacterName(), "");
    }

    @Test
    public void nameNotAllEmptySpaces() {
        String blankName = "";
        for (int i = 0; i < player.getCharacterName().length(); i++) {
            blankName += " ";
        }
        Assertions.assertNotEquals(player.getCharacterName(), blankName);
    }
}
