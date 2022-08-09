package org.openjfx.entities;

public class Coordinate {
    /**
     * Since we deal so heavily with coordinates I decided to make a
     * coordinate data object to make the code cleaner.
     *
     * Hopefully I don't end up regretting this.
     * This should also serve the purpose of input cleansing for a lot of our data.
     */

    private int x;
    private int y;
    private final int[] coordinateArray = new int[2];

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        coordinateArray[0] = x;
        coordinateArray[1] = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        coordinateArray[0] = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        coordinateArray[1] = y;
    }

    public int[] getCoordinateArray() {
        return coordinateArray;
    }

    public void setCoordinateArray(int[] coordinateArray) {
        setX(coordinateArray[0]);
        setY(coordinateArray[1]);
    }

    public void setCoordinate(Coordinate coordinate) {
        setCoordinateArray(coordinate.coordinateArray);
    }

    public Coordinate add(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }
    public Coordinate addCoordinate(Coordinate coordinate) {
        return add(coordinate.x, coordinate.y);
    }

    public Coordinate clone() {
        return new Coordinate(this.x, this.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean equals(Coordinate coordinate) {
        return this.x == coordinate.x && this.y == coordinate.y;
    }

    public int compareTo(Object o) {
        Coordinate coordinate = (Coordinate) o;
        if (x == coordinate.getX() && y == coordinate.getY()) {
            return 0;
        }
        return -1;
    }
}
