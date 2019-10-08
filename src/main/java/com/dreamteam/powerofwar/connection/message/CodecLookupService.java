package com.dreamteam.powerofwar.connection.message;

import com.dreamteam.powerofwar.connection.exception.IllegalCodecCodeException;

public interface CodecLookupService {

    /**
     * Registers the codec with message type and codec code.
     *
     * @param codecCode code of specified codec.
     * @param codec codec which can work with the specified message type.
     * @param messageType type of message.
     * @param <T> type of message
     */
    <T extends Message> void register(int codecCode, Class<T> messageType, Codec<T> codec);

    /**
     * Finds the codec by specified code and returns it.
     *
     * @param codecCode code of codec.
     * @return codec which has been registered with specified code.
     * @throws IllegalCodecCodeException if codec with specified code has not been registered.
     */
    Codec<?> find(int codecCode) throws IllegalCodecCodeException;

    /**
     * Finds the codec by specified message type and returns it.
     *
     * @param messageType class of message.
     * @param <T> type of message.
     * @return codec which has been registered with specified message type.
     * @throws IllegalCodecCodeException if codec with specified message type has not been registered.
     */
    <T extends Message> Codec<T> find(Class<T> messageType) throws IllegalCodecCodeException;

    //todo: JavaDocs
    <T extends Message> Codec.CodecRegistration getCodecRegistration(Class<T> tClass);
}
