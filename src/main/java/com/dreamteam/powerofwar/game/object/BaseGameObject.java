package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.phisics.Units;
import com.dreamteam.powerofwar.phisics.Vector;

public class BaseGameObject implements GameObject {

    private double x;
    private double y;
    private double size;
    private Vector speedVector;
    /**
     * Относительная скорость объекта. значение не должно быть отрицательным.
     */
    private double speed;

    public BaseGameObject(double x, double y, Vector speedVector, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.speedVector = Vector.byRadians(speed, speedVector.getRadians());
        this.size = 5;
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
    public double getSize() {
        return size;
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
        x += getSpeedX() * Units.SPEED * time;
        y += getSpeedY() * Units.SPEED * time;
    }

    @Override
    public void stop() {
        speedVector = new Vector();
    }

    @Override
    public void setDirection(double direction) {
        speedVector = Vector.byDirection(speed, direction);
    }

    @Override
    public void setRadians(double radians) {
        speedVector = Vector.byRadians(speed, radians);
    }

    @Override
    public boolean isInCollisionWith(GameObject gameObject) {
        boolean byX = Math.abs(gameObject.getX() - this.getX()) < (this.getSize() + gameObject.getSize());
        boolean byY = Math.abs(gameObject.getY() - this.getY()) < (this.getSize() + gameObject.getSize());

        return byX && byY;
    }


}
