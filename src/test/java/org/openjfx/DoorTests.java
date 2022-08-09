package org.openjfx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjfx.entities.Coordinate;
import org.openjfx.entities.mapObjects.Door;
import org.openjfx.entities.mapObjects.MapGenerator;
import org.openjfx.entities.mapObjects.Tile;
import org.testfx.framework.junit5.ApplicationTest;

public class DoorTests extends ApplicationTest {

    // Tests that two doors are connected correctly.
    @Test
    public void testDoorConnections() {
        Tile tile = MapGenerator.getRandomTile(new Coordinate(0, 0));
        Door door1 = new Door(new Coordinate(0, 0), tile, false);
        Door door2 = new Door(new Coordinate(0, 0), tile, false);
        door1.setConnectedDoor(door2);
        Assertions.assertEquals(door1.getConnectedDoor(), door2);

    }

    // Tests that doors linked in a circular fashion are
    // still correctly connected.
    @Test
    public void testCircularDoors() {
        Tile tile = MapGenerator.getRandomTile(new Coordinate(0, 0));
        Door door1 = new Door(new Coordinate(0, 0), tile, false);
        Door door2 = new Door(new Coordinate(0, 0), tile, false);
        Door door3 = new Door(new Coordinate(0, 0), tile, false);
        door1.setConnectedDoor(door2);
        door2.setConnectedDoor(door3);
        door3.setConnectedDoor(door1);
        Door testDoor = door3.getConnectedDoor().getConnectedDoor();
        Assertions.assertEquals(testDoor, door2);
    }
}