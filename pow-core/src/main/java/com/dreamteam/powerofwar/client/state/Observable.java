package com.dreamteam.powerofwar.client.state;

public interface Observable<T> {

    void subscribe(Subscriber<T> subscriber);

    void unsubscribe(Subscriber<T> subscriber);
}
