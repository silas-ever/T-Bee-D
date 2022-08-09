package org.openjfx.entities.mapObjects;

import org.openjfx.entities.Coordinate;

public class Door {
    private final Coordinate coordinate;
    private final Tile tile;
    private final boolean isExit;

    private final int id;
    private Directions direction = Directions.NORTH;
    private Door connectedDoor;

    public Door(Coordinate coordinate, Tile tile, boolean isExit) {
        this.coordinate = coordinate;
        this.tile = tile;
        this.isExit = isExit;
        this.id = coordinate.getX() * 7 + coordinate.getY() * 89;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Coordinate getPlayerSpawn() {
        Coordinate offsetCoordinate = coordinate.clone();
        switch (direction) {
        case NORTH:
            offsetCoordinate.setY(offsetCoordinate.getY() + 1);
            break;
        case EAST:
            offsetCoordinate.setX(offsetCoordinate.getX() + 1);
            break;
        case SOUTH:
            offsetCoordinate.setY(offsetCoordinate.getY() - 1);
            break;
        case WEST:
            offsetCoordinate.setX(offsetCoordinate.getX() - 1);
            break;
        default:
            break;
        }
        return offsetCoordinate;
    }

    public Tile getTile() {
        return tile;
    }

    public Door getConnectedDoor() {
        return connectedDoor;
    }

    public void setConnectedDoor(Door connectedDoor) {
        this.connectedDoor = connectedDoor;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public boolean isExit() {
        return isExit;
    }

    public int getId() {
        return id;
    }

    public Directions getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Door " + id + " facing " + direction + " isExit: " + isExit
                + "\nBelongs to Tile: " + tile.toString();
    }

}
