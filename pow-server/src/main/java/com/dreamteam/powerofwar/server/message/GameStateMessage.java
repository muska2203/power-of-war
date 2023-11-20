package com.dreamteam.powerofwar.server.message;

import java.util.List;
import java.util.Map;

import com.dreamteam.powerofwar.common.types.GameObjectType;
import com.dreamteam.powerofwar.common.types.ResourceType;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.server.game.object.GameObject;

public class GameStateMessage implements Message {

    private Map<ResourceType, Integer> resources;

    private List<GameObjectInfo> gameObjectsInfo;

    public GameStateMessage(Map<ResourceType, Integer> resources, List<GameObjectInfo> gameObjectsInfo) {
        this.resources = resources;
        this.gameObjectsInfo = gameObjectsInfo;
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }

    public List<GameObjectInfo> getGameObjectsInfo() {
        return gameObjectsInfo;
    }

    public static class  GameObjectInfo {

        private final int id;
        private final double x;
        private final double y;
        private final int health;
        private final GameObjectType type;
        private final boolean enemy;
        private final double size;

        public GameObjectInfo(GameObject gameObject, boolean enemy) {
            this.id = gameObject.getId();
            this.x = gameObject.getX();
            this.y = gameObject.getY();
            this.health = gameObject.getHealth();
            this.type = gameObject.getType();
            this.enemy = enemy;
            this.size = gameObject.getSize();
        }

        public int getId() {
            return id;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public int getHealth() {
            return health;
        }

        public GameObjectType getType() {
            return type;
        }

        public boolean isEnemy() {
            return enemy;
        }

        public double getSize() {
            return size;
        }
    }
}
