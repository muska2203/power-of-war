package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.player.Player;

/**
 * Фабрика игровых объектов. Добавляет проверки перед созданием объектов.
 */
public class GameObjectFactory {

    private Player player;

    public GameObjectFactory(Player player) {
        this.player = player;
    }

    public Base createBase(double x, double y) {
        if (player.getContext().getCount(GameObjectType.BASE) < 1) {
            player.getContext().addCount(GameObjectType.BASE);
            return new Base(x, y, player);
        }
        return null;
    }
}
