package com.dreamteam.powerofwar.connection.server.decoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.message.Decoder;
import com.dreamteam.powerofwar.connection.message.type.PrintTextMessage;

@Component
public class PrintTextMessageDecoder implements Decoder<PrintTextMessage> {

    @Override
    public PrintTextMessage decode(ByteBuffer byteBuffer) throws IOException {
        byte[] messageBytes = new byte[PrintTextMessage.MESSAGE_SIZE];
        byteBuffer.get(messageBytes);
        String textMessage = new String(messageBytes);
        return new PrintTextMessage(textMessage);
    }

    @Override
    public Class<PrintTextMessage> getHandledClass() {
        return PrintTextMessage.class;
    }
}
