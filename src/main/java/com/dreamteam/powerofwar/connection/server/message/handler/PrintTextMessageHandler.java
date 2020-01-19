package com.dreamteam.powerofwar.connection.server.message.handler;


import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.message.MessageHandler;
import com.dreamteam.powerofwar.connection.server.message.PrintTextMessage;

@Component
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
