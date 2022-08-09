package org.openjfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.entities.characterObjects.PlayerCharacter;

public class CharacterNameTests {

    @Test
    public void characterNameNotNull() {
        PlayerCharacter playerChar = new PlayerCharacter("Test");
        Assertions.assertNotNull(playerChar.getCharacterName());
    }

    @Test
    public void characterNameMatches() {
        PlayerCharacter playerChar = new PlayerCharacter("Test");
        Assertions.assertEquals("Test", playerChar.getCharacterName());
    }
}
