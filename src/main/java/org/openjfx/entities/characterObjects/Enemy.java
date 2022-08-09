package org.openjfx.entities.characterObjects;

public class Enemy extends Character {
    private final EnemyType enemyType;


    public Enemy(EnemyType enemyType) {
        super("", enemyType.getCharacterImageUrl(), 40);
        this.enemyType = enemyType;
        setHealth(enemyType.getInitialHealth());
        setAttackDamage(enemyType.getDamage());
        setAttackRange(enemyType.getRange());
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void attack(PlayerCharacter playerCharacter) {
        playerCharacter.startConstantAnimation();
        playerCharacter.startAttackedAnimation();
        int newHealth = playerCharacter.getHealth() - getAttackDamage();
        playerCharacter.setHealth(newHealth);
        if (playerCharacter.getHealth() <= 0) {
            playerCharacter.setFainted(true);
        }
    }

    public enum EnemyType {
        VenusFlyTrap("enemy_venus_fly_trap", 80, 25, 1),
        Hornet("enemy_hornet", 30, 20, 3),
        Ant("enemy_ant", 60, 10, 1);

        //private Enemy enemy;
        private final String characterImageUrl;
        private final int initialHealth;
        private final int damage;
        private final int range;

        EnemyType(String characterImageUrl, int health, int damage, int range) {
            this.characterImageUrl = characterImageUrl;
            this.initialHealth = health;
            this.damage = damage;
            this.range = range;
        }

        public String getCharacterImageUrl() {
            return characterImageUrl;
        }

        public int getInitialHealth() {
            return initialHealth;
        }

        public int getDamage() {
            return damage;
        }

        public int getRange() {
            return range;
        }
    }
}
