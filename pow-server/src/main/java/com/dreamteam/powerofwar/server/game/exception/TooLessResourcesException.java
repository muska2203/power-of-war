package com.dreamteam.powerofwar.server.game.exception;

import com.dreamteam.powerofwar.common.player.Player;
import com.dreamteam.powerofwar.common.types.GameObjectType;

/**
 * Исключение, которое обозначает, что объект не может быть создан,
 * так как был достигнут лимит для указанного пользователя.
 */
public class TooLessResourcesException extends RuntimeException {

    public TooLessResourcesException(GameObjectType type, Player player) {
        super("Player \"" + player.getName() + "\" doesn't have enough resources for object - " + type.toString());
    }
}
