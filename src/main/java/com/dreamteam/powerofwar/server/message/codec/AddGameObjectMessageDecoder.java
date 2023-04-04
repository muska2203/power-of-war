package com.dreamteam.powerofwar.server.message.codec;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.codec.Decoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;
import com.dreamteam.powerofwar.game.types.GameObjectType;
import com.dreamteam.powerofwar.server.message.AddGameObjectMessage;
import com.dreamteam.powerofwar.server.message.MessageTypes;

@Component
public class AddGameObjectMessageDecoder extends Decoder<AddGameObjectMessage> {

    @Override
    public AddGameObjectMessage decodeInternal(ByteBuffer byteBuffer) {
        double x = byteBuffer.getDouble();
        double y = byteBuffer.getDouble();
        GameObjectType gameObjectType = GameObjectType.valueOf(byteBuffer.getInt());
        return new AddGameObjectMessage(x, y, gameObjectType);
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.ADD_OBJECT;
    }
}
