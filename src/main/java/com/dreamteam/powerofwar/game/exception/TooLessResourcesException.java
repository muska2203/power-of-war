package com.dreamteam.powerofwar.game.exception;

import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.player.Player;

/**
 * Исключение, которое обозначает, что объект не может быть создан, так как был достигнут лимит для указанного пользователя.
 */
public class TooLessResourcesException extends RuntimeException {

    public TooLessResourcesException(GameObjectType type, Player player) {
        super("Player \"" + player.getName() + "\" doesn't have enough resources for object - " + type.toString());
    }
}
