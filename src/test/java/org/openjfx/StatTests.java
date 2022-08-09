package org.openjfx;

import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.config.Settings;
import org.openjfx.entities.characterObjects.Enemy;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.characterObjects.Weapons;
import org.openjfx.scenes.GameScene;
import org.testfx.framework.junit5.ApplicationTest;

public class StatTests extends ApplicationTest {

    private final PlayerCharacter player = new PlayerCharacter("Test", Weapons.Honey);

    @Override
    public void start(Stage primaryStage) {
        GameScene newGame = new GameScene();
        primaryStage.setScene(newGame.getStartingGameScreen(player));
        primaryStage.show();
    }

    // Tests that the player's health and regeneration are different
    // based on the selected difficulty.
    @Test
    public void testDifficultyAffectsHealthRegen() {
        Settings.setDifficulty(1);
        Enemy enemy = new Enemy(Enemy.EnemyType.Ant);
        
        enemy.attack(player);
        int health1 = player.regenHealth(); // Regen 15 hp at difficulty 1
        System.out.println("Difficulty 1 Health: " + health1);

        Settings.setDifficulty(10);
        enemy.attack(player);
        int health10 = player.regenHealth(); // Regen 1 hp at difficulty 10
        System.out.println("Difficulty 10 Health: " + health10);

        Assertions.assertNotEquals(health1, health10);
    }

    // Tests that the player's health decreases by the correct amount
    // when attacked by the venus flytrap enemy.
    @Test
    public void testVenusFlyTrapDealsDamage() {
        Settings.setDifficulty(1);
        Enemy enemy = new Enemy(Enemy.EnemyType.VenusFlyTrap);

        System.out.println("\nPlayer's Health: " + player.getHealth());
        int initHealth = player.getStartingHealth();

        enemy.attack(player);
        System.out.println("Venus Fly Trap attacks Player!");

        System.out.println("Player's Health: " + player.getHealth());
        int newHealth = player.getHealth();

        Assertions.assertNotEquals(initHealth, newHealth);
    }

    // Tests that a player faints when their health is set
    // to zero.
    @Test
    public void testPlayerFainted() {
        Enemy enemy = new Enemy(Enemy.EnemyType.VenusFlyTrap);
        for (int i = 0; i < 10; i++) {
            enemy.attack(player);
        }

        System.out.println("Player's Health: " + player.getHealth());
        System.out.println("Player fainted: " + player.isFainted());
        Assertions.assertTrue(player.isFainted());
    }

    @Test
    public void playerAttackChangesWithWeapon() {
        PlayerCharacter player1 = new PlayerCharacter("Test", Weapons.Blueberry);
        PlayerCharacter player2 = new PlayerCharacter("Test", Weapons.Honey);
        PlayerCharacter player3 = new PlayerCharacter("Test", Weapons.Sword);
        Assertions.assertNotEquals(player1.getAttackDamage(), player2.getAttackDamage());
        Assertions.assertNotEquals(player1.getAttackDamage(), player3.getAttackDamage());
        Assertions.assertNotEquals(player2.getAttackDamage(), player3.getAttackDamage());
        Assertions.assertNotEquals(player1.getAttackRange(), player2.getAttackRange());
        Assertions.assertNotEquals(player1.getAttackRange(), player3.getAttackRange());
        Assertions.assertNotEquals(player2.getAttackRange(), player3.getAttackRange());
    }
}
