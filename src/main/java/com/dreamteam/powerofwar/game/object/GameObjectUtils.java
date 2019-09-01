package com.dreamteam.powerofwar.game.object;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Утилитарный класс, который предоставляет часто используемые расчеты, связанные с игровыми объектами.
 */
class GameObjectUtils {

    /**
     * Проверяет 2 объекта на столкновение.
     *
     * @param firstObject первый.
     * @param secondObject второй.
     * @return true - если объекты пересекаются, false - в противном случае.
     */
    public static boolean checkCollision(GameObject firstObject, GameObject secondObject) {
        return getDistance(firstObject, secondObject) <= secondObject.getSize() + firstObject.getSize();
    }

    public static boolean checkVisibility(GameObject currentObject, GameObject checkedObject) {
        double criticalDist2 = Math.pow(currentObject.getVisibilityRadius() + checkedObject.getSize(), 2);
        double distByX = checkedObject.getX() - currentObject.getX();
        double distByY = checkedObject.getY() - currentObject.getY();
        double actualDist2 = distByX * distByX + distByY * distByY;
        return actualDist2 <= criticalDist2;
    }

    public static double getDistance(GameObject firstObject, GameObject secondObject) {
        double distByX = secondObject.getX() - firstObject.getX();
        double distByY = secondObject.getY() - firstObject.getY();
        double actualDist2 = distByX * distByX + distByY * distByY;
        return Math.sqrt(actualDist2);
    }

    /**
     * Возвращает ближайший объект к нужному.
     * @param current объект, для которого необходимо выбрать ближайший объект
     * @param objects набор объектов.
     * @return найденный объект, или <code>null</code>, если такого объекта не существует.
     */
    public static GameObject getNearestObject(GameObject current, Collection<GameObject> objects) {
        return getNearestObject(current, objects, (obj) -> true);
    }

    /**
     * Возвращает ближайший объект, удовлетворяющий условию предиката
     * @param current объект, для которого находится ближайший объект
     * @param objects набор объектов, из которого необходимо выбирать
     * @param predicate условие для более точного выбора
     * @return объект, подходящий условию, либо <code>null</code>, если такого не обнаружено.
     */
    public static GameObject getNearestObject(GameObject current,
                                              Collection<GameObject> objects,
                                              Predicate<GameObject> predicate) {
        if (objects == null || objects.isEmpty()) {
            return null;
        }
        double minDistance = Double.MAX_VALUE;
        GameObject nearest = null;

        for (GameObject object : objects) {
            if (predicate.test(object) && !object.isDead()) {
                double distance = getDistance(object, current);
                if (distance <= minDistance) {
                    minDistance = distance;
                    nearest = object;
                }
            }
        }

        return nearest;
    }
}
