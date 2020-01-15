package com.dreamteam.powerofwar.connection.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistryMessageDispatcher implements MessageDispatcher {

    private Map<Class<? extends Message>, MessageHandler<? extends Message>> handlers;

    public RegistryMessageDispatcher() {
        this.handlers = new HashMap<>();
    }

    public RegistryMessageDispatcher(List<MessageHandler<? extends Message>> handlers) {
        this();
        handlers.forEach(this::register);
    }

    public <T extends Message> void register(MessageHandler<T> handler) {
        this.handlers.put(handler.getHandledClass(), handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A extends Message> void dispatch(A message) {
        MessageHandler<A> actionHandler = (MessageHandler<A>) this.handlers.get(message.getClass());
        if (actionHandler == null) {
            // TODO: Log unusual situation
            return;
        }
        actionHandler.handle(message);
    }
}
