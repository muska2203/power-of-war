package com.dreamteam.powerofwar.client.message.codec;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.client.message.AddGameObjectMessage;
import com.dreamteam.powerofwar.client.message.MessageTypes;
import com.dreamteam.powerofwar.connection.codec.Encoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;

@Component
public class AddGameObjectMessageEncoder implements Encoder<AddGameObjectMessage> {

    private static final int MESSAGE_SIZE = Double.BYTES * 2 + Integer.BYTES;

    @Override
    public boolean encode(ByteBuffer byteBuffer, AddGameObjectMessage message) {
        byteBuffer.putDouble(message.getX());
        byteBuffer.putDouble(message.getY());
        byteBuffer.putInt(message.getType().getCode());
        return true;
    }

    @Override
    public int getCodingSize() {
        return MESSAGE_SIZE;
    }

    @Override
    public int getMessageSize(AddGameObjectMessage message) {
        return MESSAGE_SIZE;
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.ADD_OBJECT;
    }
}
