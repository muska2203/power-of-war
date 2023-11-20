package com.dreamteam.powerofwar.common.state;

public interface StateDispatcher<T> {

    void dispatch(T state);
}
