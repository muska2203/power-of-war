package com.dreamteam.powerofwar.game.player;

import com.dreamteam.powerofwar.game.object.type.BuildingType;
import com.dreamteam.powerofwar.game.object.type.GameObjectType;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.game.object.type.UnitType;

import java.util.HashMap;
import java.util.Map;


/**
 * Здесь содержится основная информация об игроке.
 */
public class PlayerContext {

    /**
     * Максимальное кол-во любого вида объектов для игроков по умолчанию.
     */
    private static final int MAX_COUNT = 1000;

    /**
     * Количество игровых объектов, которые принадлежат игроку
     */
    private Map<GameObjectType, Integer> countByObjectTypes = new HashMap<>();

    /**
     * Ограничения на количество игровых объектов, которые принадлежат игроку.
     * По умолчанию у всех объектов установлено ограничение - {@link PlayerContext#MAX_COUNT}
     */
    private Map<GameObjectType, Integer> objectLimitMap = new HashMap<>();

    /**
     * Капитал ресурсов игрока.
     */
    private Map<ResourceType, Integer> resources = new HashMap<>();

    /**
     * Стоимость указанного вида игровых объектов.
     * По умолчанию все объекты являются бесплатными, для добавления стоимости требуется добавить запись в указанную map-у.
     */
    private Map<GameObjectType, Map<ResourceType, Integer>> gameObjectCosts = new HashMap<>();

    PlayerContext () {
        objectLimitMap.put(BuildingType.BASE, 1);
        gameObjectCosts.put(UnitType.GOLD_MINER, Map.of(ResourceType.GOLD, 10));
    }

    /**
     * Возвращает количество объектов указанного типа, которые принадлежат игроку.
     *
     * @param type тип игровых объектов.
     * @return количество объектов.
     */
    public int getObjectCount(GameObjectType type) {
        Integer count = countByObjectTypes.get(type);
        return count != null ? count : 0;
    }

    /**
     * Признак заполненности лимита указанного типа объектов.
     *
     * @param type тип игровых объектов.
     * @return true - если лимит достигнут, false - в противном случае.
     */
    public boolean isFullOf(GameObjectType type) {
        return this.getObjectCount(type) >= getObjectLimit(type);
    }

    /**
     * Увеличивает кол-во объектов указанного типа на единицу.
     * Обозначает, что игрок построил игровой объект указаннгого типа.
     *
     * @param type тип игрового объекта.
     */
    public void addObjectCount(GameObjectType type) {
        countByObjectTypes.put(type, this.getObjectCount(type) + 1);
    }

    /**
     * Уменьшает кол-во объектов указанного типа на единице.
     * Обозначает, что игровой объект игрока был уничтожен.
     *
     * @param type тип игрового объекта.
     */
    public void minusObjectCount(GameObjectType type) {
        countByObjectTypes.put(type, this.getObjectCount(type) - 1);
    }

    /**
     * Возвращает значение лимита для указанного типа игровых объектов.
     *
     * @param type тип игровых объектов.
     * @return максимальное кол-во объектов указанного типа, которым может владеть пользователь.
     */
    public int getObjectLimit(GameObjectType type) {
        Integer limit = objectLimitMap.get(type);
        return limit != null ? limit : MAX_COUNT;
    }

    /**
     * Добавляет указанное количество ресурсов указанного типа в "казну".
     *
     * @param type тип ресурса.
     * @param count количество ресурса.
     */
    public void addResource(ResourceType type, Integer count) {
        resources.put(type, this.getResource(type) + count);
    }

    /**
     * Отнимает указанное количество ресурсов указанного типа из "казну".
     *
     * @param type тип ресурса.
     * @param count количество ресурса.
     */
    public void minusResource(ResourceType type, Integer count) {
        resources.put(type, this.getResource(type) - count);
    }

    /**
     * Возвращает количество ресурса указанного типа, которое содержится в "казне" игрока.
     *
     * @param type тип ресурса.
     * @return количество указанного ресурса.
     */
    public Integer getResource(ResourceType type) {
        Integer count = resources.get(type);
        return  count != null ? count : 0;
    }

    /**
     * Определяет, достаточно ли ресурсов находится в "казне" игрока для постройки игрового объекта указанного типа.
     *
     * @param type тип игрового объекта.
     * @return true, если ресурсов хватает. False - в противном случае.
     */
    public boolean isEnoughResourcesFor(GameObjectType type) {
        Map<ResourceType, Integer> resourceMap = gameObjectCosts.get(type);
        if (resourceMap == null || resourceMap.isEmpty()) {
            return true;
        }
        for (ResourceType resourceType : resourceMap.keySet()) {
            if (resourceMap.get(resourceType) > this.getResource(resourceType)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Отнимает нужное кол-во требуемых ресурсов для производства игрового объекта указанного типа.
     *
     * @param type тип игрового объекта.
     */
    public void minusResourcesFor(GameObjectType type) {
        Map<ResourceType, Integer> resourceMap = gameObjectCosts.get(type);
        if (resourceMap == null || resourceMap.isEmpty()) {
            return;
        }
        for (ResourceType resourceType : resourceMap.keySet()) {
            this.minusResource(resourceType, resourceMap.get(resourceType));
        }
    }

}
