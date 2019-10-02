package com.dreamteam.powerofwar.client.action;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistryActionDispatcher implements ActionDispatcher {

    private Map<Class<? extends Action>, ActionHandler<? extends Action>> handlers;

    public RegistryActionDispatcher() {
        this.handlers = new HashMap<>();
    }

    @Autowired
    @SuppressWarnings("unchecked")
    public <A extends Action> RegistryActionDispatcher(List<ActionHandler<A>> handlers) {
        this();
        handlers.forEach(actionHandler -> {
            ParameterizedType genericInterface = (ParameterizedType) actionHandler.getClass().getGenericInterfaces()[0];
            Class<A> actionClass = (Class<A>) genericInterface.getActualTypeArguments()[0];
            this.register(actionClass, actionHandler);
        });
    }

    public <T extends Action> void register(Class<T> actionClass, ActionHandler<T> handler) {
        this.handlers.put(actionClass, handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Action> void dispatch(A action) {
        ActionHandler<A> actionHandler = (ActionHandler<A>) this.handlers.get(action.getClass());
        if (actionHandler == null) {
            // TODO: Log unusual situation
            return;
        }
        actionHandler.onAction(action);
    }
}
