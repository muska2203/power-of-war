package com.dreamteam.powerofwar.game.object;

/**
 * Used for moving by direction. Define point on game board.
 */
public class Point {

    /**
     * X-coordinate
     */
    private double x;

    /**
     * Y-coordinate
     */
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
