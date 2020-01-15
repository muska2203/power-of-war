package com.dreamteam.powerofwar.connection.message;

import com.dreamteam.powerofwar.connection.exception.IllegalCodecCodeException;

public interface DecoderDispatcher {

    <T extends Message> Decoder<T> findDecoder(Class<T> messageType) throws IllegalCodecCodeException;
}
