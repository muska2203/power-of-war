package com.dreamteam.powerofwar.game.exception;

import com.dreamteam.powerofwar.game.object.GameObjectType;
import com.dreamteam.powerofwar.game.player.Player;

/**
 * Исключение, которое обозначает, что объект не может быть создан, так как был достигнут лимит для указанного пользователя.
 */
public class TooManyObjectsException extends RuntimeException {

    public TooManyObjectsException(GameObjectType type, Player player) {
        super("Player \"" + player.getName() + "\" already has too many objects with type - " + type.toString());
    }
}
