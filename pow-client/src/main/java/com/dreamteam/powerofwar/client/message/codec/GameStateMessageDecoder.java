package com.dreamteam.powerofwar.client.message.codec;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.game.object.StaticGameObject;
import com.dreamteam.powerofwar.client.message.GameStateMessage;
import com.dreamteam.powerofwar.client.message.MessageTypes;
import com.dreamteam.powerofwar.common.types.GameObjectType;
import com.dreamteam.powerofwar.common.types.ResourceType;
import com.dreamteam.powerofwar.connection.codec.Decoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;

@Component
public class GameStateMessageDecoder extends Decoder<GameStateMessage> {

    @Override
    public GameStateMessage decode(ByteBuffer byteBuffer) {
        Map<ResourceType, Integer> resources = new HashMap<>();
        for (ResourceType resourceType : ResourceType.values()) {
            resources.put(resourceType, byteBuffer.getInt());
        }
        int objectsListSize = byteBuffer.getInt();
        List<StaticGameObject> gameObjects = new ArrayList<>(objectsListSize);
        for (int i = 0; i < objectsListSize; i++) {
            StaticGameObject gameObject = new StaticGameObject();
            gameObject.setId(byteBuffer.getInt());
            gameObject.setX(byteBuffer.getDouble());
            gameObject.setY(byteBuffer.getDouble());
            gameObject.setHealth(byteBuffer.getInt());
            gameObject.setSize(byteBuffer.getDouble());
            gameObject.setType(GameObjectType.valueOf(byteBuffer.getInt()));
            gameObject.setEnemy(byteBuffer.get() == 1);
            gameObjects.add(gameObject);
        }
        return new GameStateMessage(resources, gameObjects);
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.GAME_STATE;
    }
}