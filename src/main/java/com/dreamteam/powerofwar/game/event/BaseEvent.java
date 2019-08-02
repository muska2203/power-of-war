package com.dreamteam.powerofwar.game.event;

public abstract class BaseEvent implements Event {

    protected double x;
    protected double y;

    public BaseEvent(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
