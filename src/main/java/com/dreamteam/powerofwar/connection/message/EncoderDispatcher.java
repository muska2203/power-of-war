package com.dreamteam.powerofwar.connection.message;

import com.dreamteam.powerofwar.connection.exception.IllegalCodecCodeException;

public interface EncoderDispatcher {

    /**
     * Finds the encoder by the specified message type and returns it.
     *
     * @param messageType class of message.
     * @param <T> type of message.
     * @return codec which has been registered with specified message type.
     * @throws IllegalCodecCodeException if codec with specified message type has not been registered.
     */
    <T extends Message> Encoder<T> findEncoder(Class<T> messageType) throws IllegalCodecCodeException;
}
