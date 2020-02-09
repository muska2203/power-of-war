package com.dreamteam.powerofwar.connection.clienttest.message;

import com.dreamteam.powerofwar.connection.Message;

public class PrintTextMessage implements Message {


    private String textMessage;

    public PrintTextMessage(String connectionId) {
        this.textMessage = connectionId;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
