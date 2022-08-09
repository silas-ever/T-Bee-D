package org.openjfx.entities.characterObjects;

import org.openjfx.config.Settings;

public class PlayerCharacter extends Character {
    private Weapons characterWeapon;
    private int money;

    // End screen stat variables
    private int totalMonstersKilled;
    private int totalDamageDealt;
    private int totalStepsTaken;

    //region Constructors
    public PlayerCharacter(String name, Weapons characterWeapon) {
        super(name, "", 40);
        this.characterWeapon = characterWeapon;
        this.money = getStartingMoney();
        updatePlayerCharacterImageUrl();
        setHealth(getStartingHealth());
        setAttackDamage(characterWeapon.getDamage());
        setAttackRange(characterWeapon.getRange());
    }
    public PlayerCharacter(String name) {
        this(name, Weapons.Sword);
        setAttackDamage(Weapons.Sword.getDamage());
        setAttackRange(Weapons.Sword.getRange());
    }
    //endregion

    //region Weapon methods
    public Weapons weapon() {
        return characterWeapon;
    }
    public void changeWeapon(Weapons characterWeapon) {
        this.characterWeapon = characterWeapon;
        updatePlayerCharacterImageUrl();
    }
    //endregion

    //region Money methods
    private int getStartingMoney() {
        return 110 - Settings.getDifficulty() * 10;
    }
    public int getMoney() {
        return money;
    }
    public int setMoney(int money) {
        this.money = money;
        return this.money;
    }
    public int spendMoney(int moneySpent) {
        this.money -= moneySpent;
        if (this.money < 0) {
            throw new IllegalArgumentException("Cannot Spend More Money Than Character Has!");
        }
        return this.money;
    }
    //endregion

    //region Health methods
    public int getStartingHealth() {
        if (Settings.getDifficulty() >= 5) {
            setHealth((150 - Settings.getDifficulty() * 10) + 10);
        } else {
            setHealth(150 - Settings.getDifficulty() * 10);
        }
        return getHealth();
    }
    public int regenHealth() {
        if (Settings.getDifficulty() >= 9) {
            setHealth(getHealth() + 1);
        } else if (Settings.getDifficulty() >= 5) {
            setHealth(getHealth() + 3);
        } else {
            setHealth(getHealth() + 5);
        }
        return getHealth();
    }
    //endregion

    public void attack(Enemy enemy) {
        enemy.stopAttackingAnimation();
        enemy.startConstantAnimation();
        enemy.startAttackedAnimation();
        this.stopAttackingAnimation();
        this.startConstantAnimation();

        int newHealth = enemy.getHealth() - getAttackDamage();
        enemy.setHealth(newHealth);
        if (enemy.getHealth() <= 0) {
            enemy.setFainted(true);
            //disappears
            enemy.stopConstantAnimation();
            enemy.startFaintedAnimation();
            setTotalMonstersKilled(getTotalMonstersKilled() + 1);
        }
        setTotalDamageDealt(getTotalDamageDealt() + getAttackDamage());
    }

    //region Stats methods
    public int getTotalMonstersKilled() {
        return this.totalMonstersKilled;
    }
    public void setTotalMonstersKilled(int totalMonstersKilled) {
        this.totalMonstersKilled = totalMonstersKilled;
    }
    public int getTotalDamageDealt() {
        return this.totalDamageDealt;
    }
    public void setTotalDamageDealt(int totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }
    public int getTotalStepsTaken() {
        return this.totalStepsTaken;
    }
    public void setTotalStepsTaken(int totalStepsTaken) {
        this.totalStepsTaken = totalStepsTaken;
    }
    //endregion

    //region Private Helper Methods
    private String getPlayerCharacterImageUrl() {
        return "player_bee_" + characterWeapon.getWeaponName().toLowerCase();
    }
    private void updatePlayerCharacterImageUrl() {
        setCharacterImageUrl(getPlayerCharacterImageUrl());
    }
    //endregion
}
