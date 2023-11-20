package com.dreamteam.powerofwar.client.action;

import com.dreamteam.powerofwar.common.handler.HandleableType;

/**
 * An Action is an object which indicates that a player initiated some event.
 *
 * <p>
 * Implementations of this interface may have its own fields and data that
 * can be processed by an appropriate {@link com.dreamteam.powerofwar.common.handler.Handler <Action>}
 */
public interface Action extends HandleableType {

}
