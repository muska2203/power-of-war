package com.dreamteam.powerofwar;

public class BaseGameObject implements GameObject {

    private double x;
    private double y;
    private Vector speedVector;
    private double maxSpeed;

    public BaseGameObject(double x, double y, Vector speedVector, double maxSpeed) {
        this.x = x;
        this.y = y;
        this.speedVector = speedVector;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getSpeedX() {
        return speedVector.getX();
    }

    @Override
    public double getSpeedY() {
        return speedVector.getY();
    }

    @Override
    public void move(Board board, long time) {
        x += getSpeedX() * time/100000;
        if (x > board.getWidth()) {
            x = board.getWidth();
        } else if (x < 0) {
            x = 0;
        }
        y += getSpeedY() * time/100000;
        if (y > board.getHeight()) {
            y = board.getHeight();
        } else if (y < 0) {
            y = 0;
        }
    }

    @Override
    public void stop() {
        speedVector = new Vector();
    }

    @Override
    public void setDirection(double direction) {
        speedVector = Vector.byDirection(maxSpeed, direction);
    }


}
