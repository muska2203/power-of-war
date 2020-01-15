package com.dreamteam.powerofwar.connection.message.type;

import com.dreamteam.powerofwar.connection.message.Message;

public class PrintTextMessage implements Message {

    public static final int MESSAGE_SIZE = 60;

    private String textMessage;

    public PrintTextMessage(String connectionId) {
        this.textMessage = connectionId;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
