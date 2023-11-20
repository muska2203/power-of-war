package com.dreamteam.powerofwar.common.state;

public interface State<T> extends Observable<T> {

    T get();
}
