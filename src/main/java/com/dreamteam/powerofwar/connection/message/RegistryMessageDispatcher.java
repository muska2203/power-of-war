package com.dreamteam.powerofwar.connection.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegistryMessageDispatcher implements MessageDispatcher {

    private Map<Class<? extends Message>, MessageHandler<? extends Message>> handlers;

    public RegistryMessageDispatcher() {
        this.handlers = new HashMap<>();
    }

    @Autowired
    @SuppressWarnings("unchecked")
    public <A extends Message> RegistryMessageDispatcher(List<MessageHandler<A>> handlers) {
        this();
        handlers.forEach(actionHandler -> {
            ParameterizedType genericInterface = (ParameterizedType) actionHandler.getClass().getGenericInterfaces()[0];
            Class<A> actionClass = (Class<A>) genericInterface.getActualTypeArguments()[0];
            this.register(actionClass, actionHandler);
        });
    }

    public <T extends Message> void register(Class<T> actionClass, MessageHandler<T> handler) {
        this.handlers.put(actionClass, handler);
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
