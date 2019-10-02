package com.dreamteam.powerofwar.connection.message;

public interface MessageDispatcher {

    <A extends Message> void dispatch(A message);
}
