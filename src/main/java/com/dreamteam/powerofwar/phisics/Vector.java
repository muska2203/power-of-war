package com.dreamteam.powerofwar.phisics;

import com.dreamteam.powerofwar.game.object.GameObject;
import com.dreamteam.powerofwar.game.object.Point;

public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(Vector v) {
        this.x = v.getX();
        this.y = v.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector byDirection(double length, double direction) {
        return byRadians(length, Math.toRadians(direction));
    }

    public static Vector byRadians(double length, double radians) {
        Vector vector = new Vector();
        vector.x = Math.cos(radians) * length;
        vector.y = Math.sin(radians) * length;
        return vector;
    }

    public static Vector byPoints(Point from, Point to) {
        return new Vector(to.getX() - from.getX(), to.getY() - from.getY());
    }

    public Vector negate() {
        return byRadians(this.getLength(), this.getRadians() + Math.PI);
    }

    public double getLength() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public double getLength(Vector vector) {
        return Math.sqrt(Math.pow(vector.getX() - this.getX(), 2) + Math.pow(vector.getY() - this.getY(), 2));
    }

    public void addVector(Vector vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }

    public Vector multiply(double number) {
        return new Vector(this.x *= number, this.y *= number);
    }

    public void set(Vector vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public static Vector addition(Vector v1, Vector v2) {
        return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    public double getRadians() {
        if (this.x == 0 && this.y == 0) {
            return 0;
        }
        double alpha;
        if (this.x == 0 && this.y < 0) {
            alpha = Math.toRadians(270);
        } else if (this.y >= 0 && this.x >= 0) {
            alpha = Math.atan(this.y / this.x);
        } else if (this.y >= 0 && this.x <= 0) {
            alpha = Math.toRadians(90) - Math.atan(this.x / this.y);
        } else if (this.y <= 0 && this.x <= 0) {
            alpha = Math.toRadians(180) + Math.atan(this.y / this.x);
        } else {
            alpha = Math.toRadians(270) - Math.atan(this.x / this.y);
        }
        return alpha;
    }
}
