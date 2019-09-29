package com.dreamteam.powerofwar.connection.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegistryEventDispatcher implements EventDispatcher {

    private Map<Class<? extends Event>, EventHandler<? extends Event>> handlers;

    public RegistryEventDispatcher() {
        this.handlers = new HashMap<>();
    }

    @Autowired
    @SuppressWarnings("unchecked")
    public <A extends Event> RegistryEventDispatcher(List<EventHandler<A>> handlers) {
        this();
        handlers.forEach(actionHandler -> {
            ParameterizedType genericInterface = (ParameterizedType) actionHandler.getClass().getGenericInterfaces()[0];
            Class<A> actionClass = (Class<A>) genericInterface.getActualTypeArguments()[0];
            this.register(actionClass, actionHandler);
        });
    }

    public <T extends Event> void register(Class<T> actionClass, EventHandler<T> handler) {
        this.handlers.put(actionClass, handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Event> void dispatch(A message) {
        EventHandler<A> actionHandler = (EventHandler<A>) this.handlers.get(message.getClass());
        if (actionHandler == null) {
            // TODO: Log unusual situation
            return;
        }
        actionHandler.handleMessage(message);
    }
}
