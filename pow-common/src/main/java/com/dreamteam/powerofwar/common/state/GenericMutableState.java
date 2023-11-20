package com.dreamteam.powerofwar.common.state;

public class GenericMutableState<T> extends AbstractObservable<T> implements State<T>, StateDispatcher<T> {

    private T state;

    @Override
    public void dispatch(T state) {
        this.state = state;
        notifySubscribers(state);
    }

    @Override
    public T get() {
        return this.state;
    }
}
