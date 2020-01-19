package com.dreamteam.powerofwar.connection.message.codec;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.message.Message;

public interface CodecDispatcher {

    Message decode(ByteBuffer byteBuffer) throws IOException;

    <T extends Message> boolean encode(ByteBuffer byteBuffer, T message) throws IOException;
}
