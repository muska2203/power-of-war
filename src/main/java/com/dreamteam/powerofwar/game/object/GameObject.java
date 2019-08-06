package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;

public interface GameObject {

    /**
     * Возвращает позицию объекта по оси "OX" относительно игровой доски.
     */
    double getX();

    /**
     * Возвращает позицию объекта по оси "OY" относительно игровой доски.
     */
    double getY();

    /**
     * Возвращает размер объекта.
     * <p>На данный момент все объекты в игре имеют круглый силуэт. Данная величина также обозначает радиус силуэта.
     */
    double getSize();

    /**
     * Здоровье объекта.
     */
    int getHealth();

    /**
     * Возвращает радиус области видимости объекта.
     */
    double getVisibilityRadius();

    /**
     * Возвращает радиус области, в которой указанный объект может взаимодействовать с другими объектами.
     */
    double getActionRadius();

    /**
     * Возвращает тип объекта.
     */
    GameObjectType getType();

    /**
     * Основной метод любого объекта. Здесь описывается логика объекта за определенный момент времени.
     * @param board игровое поле.
     * @param time текущее время.
     */
    void update(Board board, long time);

    /**
     * Регистрирует входящий в объект урон. Итоговый урон может быть изменен в зависимости от некоторых факторов (броня, и т.д.)
     * @param damage величина входящего урона.
     */
    void doDamage(int damage);

    /**
     * Является ли объект "мертвым".
     * <p>Используется в основном для сборщика мертвых объектов и их удаления с игровой доски.
     */
    boolean isDead();
}
