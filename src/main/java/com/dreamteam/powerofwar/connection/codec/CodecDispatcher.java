package com.dreamteam.powerofwar.connection.codec;

import java.nio.ByteBuffer;

import com.dreamteam.powerofwar.connection.Message;

public interface CodecDispatcher {

    Message decode(ByteBuffer byteBuffer);

    <T extends Message> boolean encode(ByteBuffer byteBuffer, T message);
}
