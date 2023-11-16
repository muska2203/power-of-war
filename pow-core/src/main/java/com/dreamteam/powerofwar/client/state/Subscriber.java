package com.dreamteam.powerofwar.client.state;

public interface Subscriber<T> {

    void onUpdate(T newValue);
}
