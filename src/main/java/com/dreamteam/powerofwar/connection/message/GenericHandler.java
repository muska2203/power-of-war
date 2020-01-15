package com.dreamteam.powerofwar.connection.message;

public interface GenericHandler<T> {

    Class<T> getHandledClass();
}
