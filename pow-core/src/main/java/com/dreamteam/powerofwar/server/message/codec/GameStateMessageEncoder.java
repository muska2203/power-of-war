package com.dreamteam.powerofwar.server.message.codec;

import java.nio.ByteBuffer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.codec.Encoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;
import com.dreamteam.powerofwar.game.types.ResourceType;
import com.dreamteam.powerofwar.server.message.GameStateMessage;
import com.dreamteam.powerofwar.server.message.MessageTypes;

@Component
public class GameStateMessageEncoder extends Encoder<GameStateMessage> {

    @Override
    protected void write(GameStateMessage message, ByteBuffer byteBuffer) {
        Map<ResourceType, Integer> resources = message.getResources();
        for (ResourceType resourceType : ResourceType.values()) {
            byteBuffer.putInt(resources.get(resourceType));
        }
        byteBuffer.putInt(message.getGameObjectsInfo().size());
        for (GameStateMessage.GameObjectInfo gameObjectInfo : message.getGameObjectsInfo()) {
            byteBuffer.putInt(gameObjectInfo.getId());
            byteBuffer.putDouble(gameObjectInfo.getX());
            byteBuffer.putDouble(gameObjectInfo.getY());
            byteBuffer.putInt(gameObjectInfo.getHealth());
            byteBuffer.putDouble(gameObjectInfo.getSize());
            byteBuffer.putInt(gameObjectInfo.getType().getCode());
            byteBuffer.put((byte) (gameObjectInfo.isEnemy() ? 1 : 0));
        }
    }

    @Override
    public int getMessageSize(GameStateMessage message) {
        return ResourceType.values().length * Integer.BYTES
                + Integer.BYTES
                + message.getGameObjectsInfo().size()
                * (3 * Integer.BYTES + 3 * Double.BYTES + Byte.BYTES);
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.GAME_STATE;
    }
}
