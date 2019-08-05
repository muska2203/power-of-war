package com.dreamteam.powerofwar.game.object;

/**
 * Тип строения. Строения строят юниты.
 */
public enum BuildingType {
    /**
     * База. Главное здание, при уничтожении которого игра оканчивается.
     * Тот, кто первый уничтожает базу противника, становится победителем.
     */
    BASE,

    /**
     * Казарма. Производит войнов.
     */
    BARRACK,

    /**
     * Рудник. Производит ресурсы.
     */
    MINE
}