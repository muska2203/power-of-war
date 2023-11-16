package com.dreamteam.powerofwar.client.state;

public interface StateDispatcher<T> {

    void dispatch(T state);
}
