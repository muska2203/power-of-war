package com.dreamteam.powerofwar.connection.message;

public interface EventDispatcher {

    <A extends Event> void dispatch(A message);
}
