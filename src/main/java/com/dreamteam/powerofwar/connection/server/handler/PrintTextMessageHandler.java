package com.dreamteam.powerofwar.connection.server.handler;


import com.dreamteam.powerofwar.connection.message.MessageHandler;
import com.dreamteam.powerofwar.connection.message.type.PrintTextMessage;

public class PrintTextMessageHandler implements MessageHandler<PrintTextMessage> {

    @Override
    public void handle(PrintTextMessage message) {
        System.out.println(message.getTextMessage());
    }

    @Override
    public Class<PrintTextMessage> getHandledClass() {
        return PrintTextMessage.class;
    }
}
