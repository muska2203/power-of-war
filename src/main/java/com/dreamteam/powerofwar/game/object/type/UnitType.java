package com.dreamteam.powerofwar.game.object.type;

//TODO: JavaDocs
public enum UnitType implements GameObjectType {

    /**
     * Аттакующий юнит, атакует ближайшую вражескую цель.
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