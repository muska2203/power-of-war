package com.dreamteam.powerofwar;

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
        double alpha = Math.toRadians(direction);
        Vector vector = new Vector();
        vector.x = Math.cos(alpha) * length;
        vector.y = Math.sin(alpha) * length;
        return vector;
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

    public double getAlpha() {
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
