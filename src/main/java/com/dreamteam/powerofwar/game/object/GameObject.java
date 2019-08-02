package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;

public interface GameObject {
    double getX();
    double getY();
    double getSize();
    double getSpeedX();
    double getSpeedY();
    void move(Board board, long time);
    void stop();

    /**
     * @param direction число, обозначающее направление в градусах относительно вертикальной линии.
     */
    void setDirection(double direction);

    /**
     * @param radians число, обозначающее направление в радианах относительно вертикальной линии.
     */
    void setRadians(double radians);

    boolean isInCollisionWith(GameObject gameObject);
}
