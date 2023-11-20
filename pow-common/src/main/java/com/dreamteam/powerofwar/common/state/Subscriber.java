package com.dreamteam.powerofwar.common.state;

public interface Subscriber<T> {

    void onUpdate(T newValue);
}
