package org.openjfx;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.controllers.RoomController;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.characterObjects.Weapons;
import org.testfx.framework.junit5.ApplicationTest;

public class AnimationTests extends ApplicationTest {

    private final PlayerCharacter player = new PlayerCharacter("Name", Weapons.Blueberry);
    private RoomController curRoom;

    @Test
    public void testGroupSingeInstantiation() {
        Group initialGroup = player.getCharacterView();

        player.startAttackingAnimation();
        Assertions.assertEquals(initialGroup, player.getCharacterView());
    }

    @Test
    public void testPlayerFramesSingleInstantiation() {
        ImageView initialPlayerImage1 = player.getCharacterImageFrame(1);
        ImageView initialPlayerImage2 = player.getCharacterImageFrame(2);
        ImageView initialPlayerImage3 = player.getCharacterImageFrame(3);
        ImageView initialPlayerImage4 = player.getCharacterImageFrame(4);

        Assertions.assertEquals(initialPlayerImage1, player.getCharacterImageFrame(1));
        Assertions.assertEquals(initialPlayerImage2, player.getCharacterImageFrame(2));
        Assertions.assertEquals(initialPlayerImage3, player.getCharacterImageFrame(3));
        Assertions.assertEquals(initialPlayerImage4, player.getCharacterImageFrame(4));
    }
}
