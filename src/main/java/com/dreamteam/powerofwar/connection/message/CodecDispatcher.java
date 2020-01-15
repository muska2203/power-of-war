package com.dreamteam.powerofwar.connection.message;

import java.util.Collection;

import com.dreamteam.powerofwar.connection.exception.IllegalCodecCodeException;

public class CodecDispatcher implements EncoderDispatcher, DecoderDispatcher {

    private EncoderDispatcher encoderDispatcher;
    private DecoderDispatcher decoderDispatcher;

    public CodecDispatcher(Collection<Encoder<?>> encoders, Collection<Decoder<?>> decoders) {
        encoderDispatcher = new StandardEncoderDispatcher(encoders);
        decoderDispatcher = new StandardDecoderDispatcher(decoders);
    }

    @Override
    public <T extends Message> Decoder<T> findDecoder(Class<T> messageType) throws IllegalCodecCodeException {
        return decoderDispatcher.findDecoder(messageType);
    }

    @Override
    public <T extends Message> Encoder<T> findEncoder(Class<T> messageType) throws IllegalCodecCodeException {
        return encoderDispatcher.findEncoder(messageType);
    }
}
