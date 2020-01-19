package com.dreamteam.powerofwar.connection.server.message;

import com.dreamteam.powerofwar.connection.message.Message;

public class PrintTextMessage implements Message {


    private String textMessage;

    public PrintTextMessage(String connectionId) {
        this.textMessage = connectionId;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
