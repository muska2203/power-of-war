package com.dreamteam.powerofwar.client.state;

public interface State<T> extends Observable<T> {

    T get();
}
