package com.dreamteam.powerofwar.client.message.codec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.message.GameStateMessage;
import com.dreamteam.powerofwar.client.message.MessageTypes;
import com.dreamteam.powerofwar.connection.codec.Decoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;
import com.dreamteam.powerofwar.game.object.type.ResourceType;

@Component
public class GameStateMessageDecoder implements Decoder<GameStateMessage> {

    private static final int MESSAGE_SIZE = ResourceType.values().length * 4;

    @Override
    public GameStateMessage decode(ByteBuffer byteBuffer) {
        Map<ResourceType, Integer> resources = new HashMap<>();
        for (ResourceType resourceType : ResourceType.values()) {
            resources.put(resourceType, byteBuffer.getInt());
        }
        return new GameStateMessage(resources);
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
