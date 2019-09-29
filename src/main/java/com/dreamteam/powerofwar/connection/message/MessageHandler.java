package com.dreamteam.powerofwar.connection.message;

public interface EventHandler<A extends Event> {

    void handleMessage(A message);
}
