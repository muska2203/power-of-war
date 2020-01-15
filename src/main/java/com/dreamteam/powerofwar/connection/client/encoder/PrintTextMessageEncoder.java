package com.dreamteam.powerofwar.connection.client.encoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.message.Encoder;
import com.dreamteam.powerofwar.connection.message.type.PrintTextMessage;

@Component
public class PrintTextMessageEncoder implements Encoder<PrintTextMessage> {

    @Override
    public ByteBuffer encode(ByteBuffer byteBuffer, PrintTextMessage message) throws IOException {
        byte[] messageBytes = new byte[PrintTextMessage.MESSAGE_SIZE];
        byte[] textMessageBytes = message.getTextMessage().getBytes();
        for (int i = 0; i < textMessageBytes.length && i < messageBytes.length; i++) {
            messageBytes[i] = textMessageBytes[i];
        }
        return byteBuffer.put(messageBytes);
    }

    @Override
    public Class<PrintTextMessage> getHandledClass() {
        return PrintTextMessage.class;
    }
}
