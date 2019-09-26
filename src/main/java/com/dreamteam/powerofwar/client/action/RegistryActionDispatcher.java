package com.dreamteam.powerofwar.client.action;

import java.util.HashMap;
import java.util.Map;

public class RegistryActionDispatcher implements ActionDispatcher {

    private Map<Class<? extends Action>, ActionHandler<? extends Action>> handlers;

    public RegistryActionDispatcher() {
        this.handlers = new HashMap<>();
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
