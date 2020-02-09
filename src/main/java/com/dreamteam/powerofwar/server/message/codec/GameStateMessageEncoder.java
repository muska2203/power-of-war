package com.dreamteam.powerofwar.server.message.codec;

import java.nio.ByteBuffer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.codec.Encoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;
import com.dreamteam.powerofwar.game.object.type.ResourceType;
import com.dreamteam.powerofwar.server.message.GameStateMessage;
import com.dreamteam.powerofwar.server.message.MessageTypes;

@Component
public class GameStateMessageEncoder implements Encoder<GameStateMessage> {

    private static final int MESSAGE_SIZE = ResourceType.values().length * 4;

    @Override
    public boolean encode(ByteBuffer byteBuffer, GameStateMessage message) {
        Map<ResourceType, Integer> resources = message.getResources();
        for (ResourceType resourceType : ResourceType.values()) {
            byteBuffer.putInt(resources.get(resourceType));
        }
        return true;
    }

    @Override
    public int getCodingSize() {
        return MESSAGE_SIZE;
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.GAME_STATE;
    }
}
