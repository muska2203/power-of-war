package com.dreamteam.powerofwar.handler;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: Javadocs
public class RegistryDispatcher<T extends HandleableType> implements Dispatcher<T> {

    private Map<Class<? extends T>, Handler<? extends T>> handlers;

    public RegistryDispatcher() {
        this.handlers = new HashMap<>();
    }

    public RegistryDispatcher(List<Handler<? extends T>> handlers) {
        this();
        handlers.forEach(this::register);
    }

    @SuppressWarnings("unchecked")
    public <A extends T> void register(Handler<A> handler) {
        ParameterizedType genericInterface = (ParameterizedType) handler.getClass().getGenericInterfaces()[0];
        Class<A> actionClass = (Class<A>) genericInterface.getActualTypeArguments()[0];
        this.handlers.put(actionClass, handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends T> void dispatch(A action) {
        Handler<A> handler = (Handler<A>) this.handlers.get(action.getClass());
        if (handler == null) {
            // TODO: Log unusual situation
            return;
        }
        handler.handle(action);
    }
}
