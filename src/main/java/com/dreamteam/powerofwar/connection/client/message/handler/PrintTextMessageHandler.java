package com.dreamteam.powerofwar.connection.client.message.handler;


import org.springframework.stereotype.Component;

import com.dreamteam.powerofwar.connection.client.message.PrintTextMessage;
import com.dreamteam.powerofwar.connection.message.MessageHandler;

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
