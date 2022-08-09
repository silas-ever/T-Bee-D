package org.openjfx.entities.mapObjects;

import org.openjfx.entities.Coordinate;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Pattern;

import static org.openjfx.config.Settings.ASSETS_FILEPATH;

public class MapGenerator {
    private static final String FILE_PATH = ASSETS_FILEPATH + "tiles/";
    private static final LinkedList<File> FILE_LIST = new LinkedList<>();
    private static Tile[][] map;

    public static Tile startMap(Coordinate mapSize, Coordinate spawn, Coordinate exit) {

        map = new Tile[mapSize.getX()][mapSize.getY()];

        map[spawn.getX()][spawn.getY()] = getStartingTile(spawn);
        map[exit.getX()][exit.getY()] = getExitTile(exit);
        return map[spawn.getX()][spawn.getY()];
    }

    public static Door getNextDoor(Door door) {
        Tile tile = door.getTile();
        if (door.getConnectedDoor() == null) {
            Coordinate spawnCoordinate =
                    getSpawnCoordinate(tile.getCoordinate(), door.getDirection());
            if (map[spawnCoordinate.getX()][spawnCoordinate.getY()] != null) {
                return map[spawnCoordinate.getX()][spawnCoordinate.getY()]
                        .getDoorByDirection(getFlippedDirection(door.getDirection()));
            }
            DoorState[] canHaveDoors = {
                DoorState.CAN_HAVE,
                DoorState.CAN_HAVE,
                DoorState.CAN_HAVE,
                DoorState.CAN_HAVE
            };
            canHaveDoors[getFlippedDirection(door.getDirection()).ordinal()] = DoorState.MUST_HAVE;
            for (Directions direction:Directions.values()) {
                Coordinate offsetCoordinate = getSpawnCoordinate(spawnCoordinate, direction);
                if (isInBounds(offsetCoordinate)) {
                    if (map[offsetCoordinate.getX()][offsetCoordinate.getY()] != null) {
                        Door nextDoor = map[offsetCoordinate.getX()][offsetCoordinate.getY()]
                                .getDoorByDirection(getFlippedDirection(direction));
                        if (nextDoor == null) {
                            canHaveDoors[direction.ordinal()] = DoorState.CANNOT_HAVE;
                        } else {
                            canHaveDoors[direction.ordinal()] = DoorState.MUST_HAVE;
                        }
                    }
                } else {
                    canHaveDoors[direction.ordinal()] = DoorState.CANNOT_HAVE;
                }
            }
            Tile nextRoom = getTileWithDoors(canHaveDoors, spawnCoordinate);
            map[spawnCoordinate.getX()][spawnCoordinate.getY()] = nextRoom;
            door.setConnectedDoor(nextRoom.getDoorByDirection(
                    getFlippedDirection(door.getDirection())));
            door.getConnectedDoor().setConnectedDoor(door);
        }
        return door.getConnectedDoor();
    }

    private static boolean isInBounds(Coordinate coordinate) {
        return (coordinate.getX() >= 0 && coordinate.getX() < getMaxMapX()
                && coordinate.getY() >= 0 && coordinate.getY() < getMaxMapY());
    }
    private static int getMaxMapX() {
        return map.length;
    }
    private static int getMaxMapY() {
        return map[0].length;
    }

    private static Directions getFlippedDirection(Directions direction) {
        return Directions.values()[(direction.ordinal() + 2) % 4];
    }

    private static Coordinate getSpawnCoordinate(
            Coordinate coordinate, Directions direction) {

        Coordinate newCoordinate = coordinate.clone();
        switch (direction) {
        case NORTH:
            newCoordinate.setY(newCoordinate.getY() - 1);
            break;
        case EAST:
            newCoordinate.setX(newCoordinate.getX() - 1);
            break;
        case SOUTH:
            newCoordinate.setY(newCoordinate.getY() + 1);
            break;
        case WEST:
            newCoordinate.setX(newCoordinate.getX() + 1);
            break;
        default:
            break;
        }
        return newCoordinate;
    }

    private enum DoorState {
        MUST_HAVE,
        CAN_HAVE,
        CANNOT_HAVE
    }
    private static Tile getTileWithDoors(DoorState[] canHaveDoor, Coordinate location) {
        while (true) {
            boolean isValidTile = true;
            Tile tile = getRandomTile(location);
            for (Directions direction:Directions.values()) {
                if (canHaveDoor[direction.ordinal()] == DoorState.MUST_HAVE
                        && tile.getDoorByDirection(direction) == null) {
                    isValidTile = false;
                }
                if (canHaveDoor[direction.ordinal()] == DoorState.CANNOT_HAVE
                        && tile.getDoorByDirection(direction) != null) {
                    isValidTile = false;
                }
            }
            if (isValidTile) {
                return tile;
            }
        }
    }

    public static Tile getRandomTile(Coordinate location) {
        if (FILE_LIST.size() == 0) {
            getRandomTileList();
        }
        return new Tile(FILE_LIST.remove(), location);
    }

    public static Tile getTileFromName(String slabFileName, Coordinate location) {
        return new Tile(new File(FILE_PATH + slabFileName), location);
    }
    public static Tile getStartingTile(Coordinate location) {
        return getTileFromName("tileStart.ti", location);
    }
    public static Tile getExitTile(Coordinate location) {
        return getTileFromName("tileExit.ti", location);
    }
    private static void getRandomTileList() {
        File directory = new File(FILE_PATH);
        String regex = "tileRandom_\\d*\\.ti";
        File[] tileFiles = listFilesMatching(directory, regex);
        LinkedList<Integer> randomNumbers = new LinkedList<>();
        for (int i = 0; i < tileFiles.length; i++) {
            randomNumbers.add(i);
        }
        Random random = new Random();
        for (int i = 0; i < tileFiles.length; i++) {
            int randomIndex = random.nextInt(randomNumbers.size());
            FILE_LIST.add(tileFiles[randomNumbers.get(randomIndex)]);
            randomNumbers.remove(randomIndex);
        }
    }

    private static File[] listFilesMatching(File root, String regex) {
        if (!root.isDirectory()) {
            throw new IllegalArgumentException(root + " is no directory.");
        }
        final Pattern p = Pattern.compile(regex);
        return root.listFiles(file -> p.matcher(file.getName()).matches());
    }
}
