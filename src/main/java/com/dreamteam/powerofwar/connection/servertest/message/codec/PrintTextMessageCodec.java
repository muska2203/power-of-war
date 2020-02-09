package com.dreamteam.powerofwar.connection.servertest.message.codec;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.codec.Codec;
import com.dreamteam.powerofwar.connection.codec.Decoder;
import com.dreamteam.powerofwar.connection.codec.Encoder;
import com.dreamteam.powerofwar.connection.codec.OPCode;
import com.dreamteam.powerofwar.connection.servertest.message.MessageTypes;
import com.dreamteam.powerofwar.connection.servertest.message.PrintTextMessage;

@Component
public class PrintTextMessageCodec implements Encoder<PrintTextMessage>, Decoder<PrintTextMessage> {

    public static final int MESSAGE_SIZE = 60;

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

    @Override
    public int getCodingSize() {
        return MESSAGE_SIZE;
    }

    @Override
    public OPCode getOPCode() {
        return MessageTypes.PRINT_TEXT_MESSAGE;
    }
}
