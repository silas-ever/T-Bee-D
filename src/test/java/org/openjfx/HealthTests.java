package org.openjfx;

import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.entities.characterObjects.Enemy;
import org.openjfx.entities.characterObjects.PlayerCharacter;
import org.openjfx.entities.characterObjects.Weapons;
import org.openjfx.scenes.GameScene;
import org.testfx.framework.junit5.ApplicationTest;

public class HealthTests extends ApplicationTest {
    private final PlayerCharacter playerCharacter =
            new PlayerCharacter("Player 1", Weapons.Sword);
    private final Enemy enemy = new Enemy(Enemy.EnemyType.Hornet);
    private final Enemy enemy2 = new Enemy(Enemy.EnemyType.Ant);
    private final Enemy enemy3 = new Enemy(Enemy.EnemyType.VenusFlyTrap);

    @Override
    public void start(Stage primaryStage) {
        GameScene newGame = new GameScene();
        primaryStage.setScene(newGame.getStartingGameScreen(playerCharacter));
        enemy.getCharacterView();
        enemy2.getCharacterView();
        enemy3.getCharacterView();
        primaryStage.show();
    }

    @Test
    public void playerAttacksEnemy() {
        int initialHealth = enemy.getHealth();
        playerCharacter.attack(enemy);
        Assertions.assertNotEquals(initialHealth, enemy.getHealth());
    }

    @Test
    public void enemyFaints() {
        while (enemy.getHealth() > 0) {
            playerCharacter.attack(enemy);
        }
        Assertions.assertTrue(enemy.isFainted() && enemy.getHealth() <= 0);
    }

    @Test
    public void tracksDamageDealt() {
        playerCharacter.attack(enemy);
        playerCharacter.attack(enemy2);
        playerCharacter.attack(enemy3);
        Assertions.assertEquals(playerCharacter.getTotalDamageDealt(),
                playerCharacter.getAttackDamage() * 3);
    }

    @Test
    public void tracksEnemiesKilled() {
        while (enemy.getHealth() > 0) {
            playerCharacter.attack(enemy);
        }
        while (enemy2.getHealth() > 0) {
            playerCharacter.attack(enemy2);
        }
        while (enemy3.getHealth() > 0) {
            playerCharacter.attack(enemy3);
        }
        Assertions.assertEquals(playerCharacter.getTotalMonstersKilled(), 3);
    }

    @Test
    public void numberOfEnemies() {
        int numOfEnemies = 0;
        for (Enemy.EnemyType ignored : Enemy.EnemyType.values()) {
            numOfEnemies++;
        }
        Assertions.assertTrue(numOfEnemies >= 3);
    }
}
