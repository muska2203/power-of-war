package com.dreamteam.powerofwar.common.state;

public interface Observable<T> {

    void subscribe(Subscriber<T> subscriber);

    void unsubscribe(Subscriber<T> subscriber);
}
