package com.dreamteam.powerofwar.connection.exception;

public class TooSmallBufferSizeException extends RuntimeException {

    public TooSmallBufferSizeException() {
    }

    public TooSmallBufferSizeException(String message) {
        super(message);
    }
}
