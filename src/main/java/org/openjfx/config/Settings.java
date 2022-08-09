package org.openjfx.config;

public class Settings {

    private static int difficulty = 0;
    public static final int PASS_FAIL_COUNT = 5;
    public static void setDifficulty(double newDifficulty) {
        difficulty = (int) Math.round(newDifficulty);
    }
    public static int getDifficulty() {
        return difficulty;
    }
    public static int getMinimumEnemies() {
        // 1-2 (1 enemy) 3-5 (2 enemies) 5-8 (3 enemies) 9-1 (4 enemies)
        return difficulty / 3 + 1;
    }
    public static int getMaximumEnemies() {
        return difficulty / 2 + getMinimumEnemies();
    }
    public static int getSpawnProbability() {
        //100 == 100% chance 0 == 0% chance
        return difficulty;
    }

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 850;
    public static final String APP_NAME = "T-Bee-D M4";

    public static final String ASSETS_FILEPATH = "src/assets/";
    private static final String ASSETS_URL = "file:" + ASSETS_FILEPATH;

    public static String getImageUrl(String fileName) {
        return ASSETS_URL + "images/" + fileName + ".png";
    }
    public static String getSoundUrl(String fileName) {
        return ASSETS_URL + "sounds/" + fileName + ".mp3";
    }

}
