package com.dreamteam.powerofwar.common.handler;

//TODO: Javadocs
public interface Handler<T extends HandleableType> {

    void handle(T action);
}
