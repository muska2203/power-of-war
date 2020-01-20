package com.dreamteam.powerofwar.connection.server.message.codec;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.message.codec.Codec;
import com.dreamteam.powerofwar.connection.message.codec.OPCode;
import com.dreamteam.powerofwar.connection.server.message.MessageTypes;
import com.dreamteam.powerofwar.connection.server.message.PrintTextMessage;

@Component
public class PrintTextMessageCodec implements Codec<PrintTextMessage> {

    public static final int MESSAGE_SIZE = 60;

    @Override
    public PrintTextMessage decode(ByteBuffer byteBuffer) {
        byte[] messageBytes = new byte[MESSAGE_SIZE];
        byteBuffer.get(messageBytes);
        String textMessage = new String(messageBytes);
        return new PrintTextMessage(textMessage);
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
    public int codingSize() {
        return MESSAGE_SIZE;
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.PRINT_TEXT_MESSAGE;
    }

    @Override
    public Class<PrintTextMessage> getHandledClass() {
        return PrintTextMessage.class;
    }
}
