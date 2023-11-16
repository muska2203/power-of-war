package com.dreamteam.powerofwar.handler;

//TODO: Javadocs
public interface Handler<T extends HandleableType> {

    void handle(T action);
}
