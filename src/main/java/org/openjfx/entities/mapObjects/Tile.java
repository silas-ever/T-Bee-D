package org.openjfx.entities.mapObjects;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import org.openjfx.config.Settings;
import org.openjfx.entities.Coordinate;
import org.openjfx.entities.characterObjects.Character;
import org.openjfx.entities.characterObjects.Enemy;

import java.io.File;
import java.util.*;

public class Tile {
    private final GridPane grid;
    private final ArrayList<ArrayList<Boolean>> hitBox = new ArrayList<>();
    private final HashMap<Integer, Door> doorHashMap = new HashMap<>();
    private Coordinate spawn = new Coordinate(1, 1);
    private final Coordinate tileLocation;
    private final HashSet<Enemy> enemies = new HashSet<>();
    private final Random random = new Random();

    private final Door[] directionalDoors = new Door[Directions.values().length];

    public Tile(File file, Coordinate location) {
        this.grid = getGridFromFile(file);
        tileLocation = location;
        setDoors();
        addEnemies();
    }

    public Door getDoorByDirection(Directions direction) {
        return directionalDoors[direction.ordinal()];
    }

    private GridPane getGridFromFile(File file) {
        GridPane pane = new GridPane();
        Directions direction = Directions.NORTH;
        try {
            // pass the path to the file as a parameter
            Scanner sc = new Scanner(file);
            ArrayList<String> lines = new ArrayList<>();
            int longestLine = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine().toLowerCase();
                if (line.split("", 0)[0].equals("#")) {
                    if (line.contains("#random")) {
                        ArrayList<Directions> validDirections = new ArrayList<>();
                        String randomLine = line.replace("#random", "");
                        for (Directions d:Directions.values()) {
                            String firstLetter =
                                    d.toString().split("", 0)[0].toLowerCase();
                            if (randomLine.contains(firstLetter)) {
                                validDirections.add(d);
                            }
                        }
                        if (validDirections.size() == 0) {
                            direction = Directions.values()
                                    [random.nextInt(Directions.values().length)];
                        } else {
                            direction = validDirections.get(random.nextInt(validDirections.size()));
                        }
                    }
                    continue;
                }
                lines.add(line);
                if (line.length() > longestLine) {
                    longestLine = line.length();
                }
            }
            for (int i = 0; i < lines.size(); i++) {
                StringBuilder line = new StringBuilder(lines.get(i));
                while (line.length() < longestLine) {
                    line.append(" ");
                }
                lines.set(i, line.toString());
            }
            switch (direction) {
            case EAST:
                lines = rotateLines90(lines);
                pane = getGridFromLinesByRow(lines);
                break;
            case SOUTH:
                lines = rotateLines180(lines);
                pane = getGridFromLinesByRow(lines);
                break;
            case WEST:
                lines = rotateLines180(lines);
                lines = rotateLines90(lines);
                pane = getGridFromLinesByRow(lines);
                break;
            default:
                pane = getGridFromLinesByRow(lines);
            }
        } catch (Exception ignored) {
            System.out.println("FILE NOT FOUND!");
        }
        pane.setHgap(3);
        pane.setVgap(3);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private ArrayList<String> rotateLines90(ArrayList<String> lines) {
        ArrayList<String> newLines = new ArrayList<>();
        int loop = 0;
        boolean hasChar = true;
        while (hasChar) {
            hasChar = false;
            StringBuilder newLine = new StringBuilder();
            for (String line:lines) {
                if (loop < line.length()) {
                    newLine.append(line.charAt(loop));
                    hasChar = true;
                }
            }
            newLines.add(newLine.toString());
            loop++;
        }
        return newLines;
    }

    private ArrayList<String> rotateLines180(ArrayList<String> lines) {
        ArrayList<String> newLines = new ArrayList<>();
        for (int i = lines.size() - 1; i >= 0; i--) {
            newLines.add(lines.remove(i));
        }
        return newLines;
    }

    private GridPane getGridFromLinesByRow(ArrayList<String> lines) {
        GridPane pane = new GridPane();
        Coordinate coordinate = new Coordinate(0, 0);
        while (lines.size() > 0) {
            String line = lines.remove(0);
            ArrayList<Boolean> lineHitBox = new ArrayList<>();
            if (line.split("", 0)[0].equals("#")) {
                //This allows comments using the "#" symbol.
                continue;
            }
            for (String character : line.split("")) {
                boolean isObstacle = true; //This forces all out-of-bounds areas to be
                // collidable. This is more for the enemy spawn logic than anything
                for (Obstacles obstacle:Obstacles.values()) {
                    if (character.equals(obstacle.getSymbol())) {
                        pane.add(obstacle.getShape(), coordinate.getX(), coordinate.getY());
                        isObstacle = obstacle.getHasCollision();
                        switch (obstacle) {
                        case DoorTile:
                            Door door = new Door(coordinate, this, false);
                            doorHashMap.put(door.getId(), door);
                            break;
                        case ExitTile:
                            door = new Door(coordinate, this, true);
                            doorHashMap.put(door.getId(), door);
                            break;
                        case SpawnTile:
                            spawn = coordinate.clone();
                            break;
                        case EnemyTile:
                            addEnemy(coordinate);
                            break;
                        default:
                            break;
                        }
                        break;
                    }
                }
                lineHitBox.add(!isObstacle);
                coordinate = coordinate.add(1, 0);
            }
            hitBox.add(lineHitBox);
            coordinate = coordinate.add(0, 1);
            coordinate.setX(0);
        }
        return pane;
    }

    private void addEnemies() {
        int passFailCount = Settings.PASS_FAIL_COUNT;
        while (enemies.size() < Settings.getMinimumEnemies() && passFailCount > 0) {
            System.out.println("PASS: " + passFailCount);
            passFailCount--;
            Coordinate coordinate = new Coordinate(0, 0);
            for (ArrayList<Boolean> row : hitBox) {
                for (boolean ignored : row) {
                    if (randomCheckAddEnemy(coordinate)) {
                        addEnemy(coordinate);
                        passFailCount = Settings.PASS_FAIL_COUNT;
                    } else if (passFailCount == 0 && enemies.size() == 0) {
                        //If no enemies have been added and the program is about to error out,
                        //spawn an enemy at the first available spawn point and return
                        if (checkAddEnemy(coordinate)) {
                            System.out.println("FORCE");
                            addEnemy(coordinate);
                            return;
                        }
                    }
                    coordinate = coordinate.add(1, 0);
                }
                coordinate.setX(0);
                coordinate = coordinate.add(0, 1);
            }
        }
    }
    private boolean checkAddEnemy(Coordinate coordinate) {
        if (enemies.size() >= Settings.getMaximumEnemies()) {
            return false;
        }
        if (!isInBounds(coordinate) || isDoorAdjacent(coordinate)) {
            return false;
        }
        for (Character enemy : enemies) {
            if (enemy.getCoordinate().equals(coordinate)) {
                return false;
            }
        }
        return true;
    }

    private boolean randomCheckAddEnemy(Coordinate coordinate) {
        if (checkAddEnemy(coordinate)) {
            int spawnNumber = random.nextInt(101);
            return spawnNumber <= Settings.getSpawnProbability();
        } else {
            return false;
        }
    }

    private boolean isDoorAdjacent(Coordinate coordinate) {
        for (Door door : doorHashMap.values()) {
            Coordinate maxCoord = door.getCoordinate().add(1, 1);
            Coordinate minCoord = door.getCoordinate().add(-1, -1);
            if (coordinate.getX() >= minCoord.getX() && coordinate.getX() <= maxCoord.getX()) {
                if (coordinate.getY() >= minCoord.getY() && coordinate.getY() <= maxCoord.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addEnemy(Coordinate coordinate) {
        int enemyChoice = random.nextInt(Enemy.EnemyType.values().length);
        Enemy enemy = new Enemy(Enemy.EnemyType.values()[enemyChoice]);
        enemy.setCoordinate(coordinate.clone());
        enemies.add(enemy);
    }

    private int doorHash(Coordinate coordinate) {
        return coordinate.getX() * 7 + coordinate.getY() * 89;
    }

    private void setDoors() {
        for (Door door:doorHashMap.values()) {
            Directions facing;
            if (isInBounds(door.getCoordinate().add(1, 0))) {
                facing = Directions.EAST;
            } else if (isInBounds(door.getCoordinate().add(-1, 0))) {
                facing = Directions.WEST;
            } else if (isInBounds(door.getCoordinate().add(0, -1))) {
                facing = Directions.SOUTH;
            } else {
                facing = Directions.NORTH;
            }
            door.setDirection(facing);
            directionalDoors[facing.ordinal()] = door;
        }
    }

    public boolean isDoor(Coordinate coordinate) {
        return doorHashMap.containsKey(doorHash(coordinate));
    }

    public Door getDoor(Coordinate coordinate) {
        if (doorHashMap.containsKey(doorHash(coordinate))) {
            return doorHashMap.get(doorHash(coordinate));
        }
        return null;
    }

    public boolean isInBounds(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (y < 0 || x < 0 || y >= hitBox.size() || x >= hitBox.get(y).size()) {
            return false;
        }
        return hitBox.get(y).get(x);
    }
    public GridPane getTile() {
        return grid;
    }
    public int getMaxX() {
        return hitBox.get(0).size();
    }
    public int getMaxY() {
        return hitBox.size();
    }
    public Coordinate getCoordinate() {
        return tileLocation;
    }

    public Coordinate getSpawn() {
        return spawn;
    }

    public HashMap<Integer, Door> getDoorHashMap() {
        return doorHashMap;
    }

    public HashSet<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public String toString() {
        StringBuilder doorString = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (directionalDoors[i] != null) {
                doorString.append(" ")
                        .append(i)
                        .append(": ")
                        .append(directionalDoors[i].getId());
            }
        }
        return "Tile " + this.hashCode()
                +  " of size: " + getMaxX() + "x" + getMaxY()
                + " Located at: " + getCoordinate()
                +  doorString;
    }
}
