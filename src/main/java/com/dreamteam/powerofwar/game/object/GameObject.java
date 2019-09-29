package com.dreamteam.powerofwar.game.object;

import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.player.Player;

public interface GameObject {

    /**
     * Возвращает уникальный идентификатор объекта
     */
    int getId();

    /**
     * All game objects have point on game board. Point is defining gameobject location.
     * @return point on game board
     */
    Point getPoint();

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
     * Возвращает коэффициент, на который будет умножена скорость юнита.
     * Данный коэффициент может зависить, например, от количества ресурсов, которые несет рабочий.
     * Некоторые юниты могут передвигаться медленнее или быстрее при определенном количестве здоровья.
     *
     * @return коэффициент скорости
     */
    double getSpeedFactor();

    /**
     * Основной метод любого объекта. Здесь описывается логика объекта в зависимости от окружения.
     * @param board игровое поле.
     */
    void update(Board board);

    /**
     * Метод, который производит изменение координат объекта в зависимости от времени игрового цикла и от внутреннего состояния.
     *
     * @param loopTime время игрового цикла.
     */
    void move(long loopTime);

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

    /**
     * Возвращает владельца текущего объекта.
     *
     * @return пользователь-владелец объекта.
     */
    Player getOwner();
}
