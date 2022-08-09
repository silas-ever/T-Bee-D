package org.openjfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.entities.characterObjects.PlayerCharacter;

public class CharacterMoneyTests {
    @Test
    public void testSpendMoney() {
        PlayerCharacter test = new PlayerCharacter("Test");
        test.setMoney(0);
        Assertions.assertEquals(test.getMoney(), 0);
        Assertions.assertEquals(test.setMoney(50), 50);
        Assertions.assertEquals(test.spendMoney(25), 25);
    }

    @Test
    public void moneyNonNegative() {
        PlayerCharacter test = new PlayerCharacter("Test");
        test.setMoney(0);
        Assertions.assertEquals(test.getMoney(), 0);
        Assertions.assertEquals(test.setMoney(50), 50);
        Assertions.assertEquals(test.spendMoney(50), 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> test.spendMoney(1));
    }
}
