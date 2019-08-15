package com.dreamteam.powerofwar.game.object;

/**
 * Утилитарный класс, который предоставляет часто используемые расчеты, связанные с игровыми объектами.
 */
public class GameObjectUtils {

    /**
     * Проверяет 2 объекта на столкновение.
     *
     * @param firstObject первый.
     * @param secondObject второй.
     * @return true - если объекты пересекаются, false - в противном случае.
     */
    public static boolean checkCollision(GameObject firstObject, GameObject secondObject) {
        double criticalDist2 = Math.pow(secondObject.getSize() + firstObject.getSize(), 2);
        double distByX = secondObject.getX() - firstObject.getX();
        double distByY = secondObject.getY() - firstObject.getY();
        double actualDist2 = distByX * distByX + distByY * distByY;
        return actualDist2 <= criticalDist2;
    }

    public static boolean checkVisibility(GameObject currentObject, GameObject checkedObject) {
        double criticalDist2 = Math.pow(currentObject.getVisibilityRadius() + checkedObject.getSize(), 2);
        double distByX = checkedObject.getX() - currentObject.getX();
        double distByY = checkedObject.getY() - currentObject.getY();
        double actualDist2 = distByX * distByX + distByY * distByY;
        return actualDist2 <= criticalDist2;
    }
}
