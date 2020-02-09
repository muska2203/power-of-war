package com.dreamteam.powerofwar.connection.clienttest.message.codec;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.clienttest.message.MessageTypes;
import com.dreamteam.powerofwar.connection.clienttest.message.PrintTextMessage;
import com.dreamteam.powerofwar.connection.codec.Decoder;
import com.dreamteam.powerofwar.connection.codec.Encoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;

@Component
public class PrintTextMessageCodec implements Encoder<PrintTextMessage>, Decoder<PrintTextMessage> {

    public static final int MESSAGE_SIZE = 60;

    @Override
    public int getCodingSize() {
        return MESSAGE_SIZE;
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.PRINT_TEXT_MESSAGE;
    }

    @Override
    public PrintTextMessage decode(ByteBuffer byteBuffer) {
        byte[] messageBytes = new byte[MESSAGE_SIZE];
        byteBuffer.get(messageBytes);
        String textMessage = new String(messageBytes);
        return new PrintTextMessage(textMessage);
    }

    @Override
    public boolean encode(ByteBuffer byteBuffer, PrintTextMessage message) {
        byte[] messageBytes = new byte[MESSAGE_SIZE];
        byte[] textMessageBytes = message.getTextMessage().getBytes();
        for (int i = 0; i < textMessageBytes.length && i < messageBytes.length; i++) {
            messageBytes[i] = textMessageBytes[i];
        }
        byteBuffer.put(messageBytes);
        return true;
    }
}
