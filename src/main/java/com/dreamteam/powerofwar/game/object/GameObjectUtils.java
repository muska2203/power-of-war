package com.dreamteam.powerofwar.game.object;

import java.util.Collection;

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

    public static GameObject getNearestObject(GameObject current, Collection<GameObject> objects) {
        double minDistance = Double.MAX_VALUE;
        GameObject nearest = null;

        for (GameObject object : objects) {
            double distance = getDistance(object, current);
            if (distance <= minDistance) {
                minDistance = distance;
                nearest = object;
            }
        }

        return nearest;
    }
}
