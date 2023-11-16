package com.dreamteam.powerofwar.client.state;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A base thread-safe implementation of an observable object.
 *
 * Derived observable classes my only need to call {@link AbstractObservable::notifySubscribers}
 * after its internal state has been changed.
 *
 * @param <T> the type of a subject class that this observable object watches.
 */
public abstract class AbstractObservable<T> implements Observable<T> {

    private List<Subscriber<T>> subscribers;

    public AbstractObservable() {
        this.subscribers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber<T> subscriber) {
        this.subscribers.remove(subscriber);
    }

    protected void notifySubscribers(T newValue) {
        this.subscribers.forEach(subscriber -> subscriber.onUpdate(newValue));
    }
}
