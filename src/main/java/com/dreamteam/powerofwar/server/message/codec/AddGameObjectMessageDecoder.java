package com.dreamteam.powerofwar.server.message.codec;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.codec.Decoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;
import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.server.message.AddGameObjectMessage;
import com.dreamteam.powerofwar.server.message.MessageTypes;

@Component
public class AddGameObjectMessageDecoder implements Decoder<AddGameObjectMessage> {

    private static final int MESSAGE_SIZE = Double.BYTES * 2 + Integer.BYTES;

    @Override
    public AddGameObjectMessage decode(ByteBuffer byteBuffer) {
        double x = byteBuffer.getDouble();
        double y = byteBuffer.getDouble();
        GameObjectType gameObjectType = GameObjectType.valueOf(byteBuffer.getInt());
        return new AddGameObjectMessage(x, y, gameObjectType);
    }

    @Override
    public int getCodingSize() {
        return MESSAGE_SIZE;
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.ADD_OBJECT;
    }
}
