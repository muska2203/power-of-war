package com.dreamteam.powerofwar.server.game.event;

public abstract class BaseEvent implements Event {

    protected double x;
    protected double y;

    BaseEvent(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
