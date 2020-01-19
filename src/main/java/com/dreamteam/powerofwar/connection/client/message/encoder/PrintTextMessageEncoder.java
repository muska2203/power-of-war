package com.dreamteam.powerofwar.connection.client.message.encoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.client.message.PrintTextMessage;
import com.dreamteam.powerofwar.connection.message.codec.Codec;
import com.dreamteam.powerofwar.connection.message.codec.OPCode;
import com.dreamteam.powerofwar.connection.client.message.MessageTypes;

@Component
public class PrintTextMessageEncoder implements Codec<PrintTextMessage> {

    public static final int MESSAGE_SIZE = 60;

    @Override
    public int codingSize() {
        return MESSAGE_SIZE;
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.PRINT_TEXT_MESSAGE;
    }

    @Override
    public boolean encode(ByteBuffer byteBuffer, PrintTextMessage message) throws IOException {
        byte[] messageBytes = new byte[MESSAGE_SIZE];
        byte[] textMessageBytes = message.getTextMessage().getBytes();
        for (int i = 0; i < textMessageBytes.length && i < messageBytes.length; i++) {
            messageBytes[i] = textMessageBytes[i];
        }
        byteBuffer.put(messageBytes);
        return true;
    }

    @Override
    public Class<PrintTextMessage> getHandledClass() {
        return PrintTextMessage.class;
    }
}
