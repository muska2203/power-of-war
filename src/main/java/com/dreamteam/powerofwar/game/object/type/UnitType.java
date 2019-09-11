package com.dreamteam.powerofwar.game.object.type;

//TODO: JavaDocs
public enum UnitType implements GameObjectType {

    /**
     * Атакующий юнит, атакует ближайшую вражескую цель.
     */
    WARRIOR,

    /**
     * Бегун.
     */
    COWARD,

    /**
     * Золотодобытчик
     */
    GOLD_MINER
}