package com.dreamteam.powerofwar;

public interface GameObject {
    double getX();
    double getY();
    double getSpeedX();
    double getSpeedY();
    void move(Board board, long time);
    void stop();

    /**
     * @param direction число, обозначающее направление в градусах относительно вертикальной линии.
     */
    void setDirection(double direction);
}
