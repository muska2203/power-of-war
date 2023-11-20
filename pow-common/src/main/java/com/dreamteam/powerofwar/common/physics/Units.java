package com.dreamteam.powerofwar.common.physics;

/**
 * Все единицы измерения, используемые в игре.
 */
final public class Units {

    /**
     * Количество пикселей, которое пройдет объект с скоростью = 1 за 1 наносекунду.
     */
    public static final double SPEED = 0.00000001;
    public static final double MINION_SIZE = 5;
    public static final double MINION_DEFAULT_VISIBILITY_RADIUS = 300;
    public static final double MINION_DEFAULT_ACTION_RADIUS = 30;
    public static final double BUILDING_SIZE = 10;
    public static final double BUILDING_DEFAULT_VISIBILITY_RADIUS = 70;
    public static final double BUILDING_DEFAULT_ACTION_RADIUS = 40;

    private Units() {
    }
}
