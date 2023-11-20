package com.dreamteam.powerofwar.connection;

import com.dreamteam.powerofwar.common.handler.Handler;

/**
 * Implementations can handle specific types of messages.
 *
 * @param <T> a type of message which an implementation handles.
 */
public interface MessageHandler<T extends Message> extends Handler<T> {
}
