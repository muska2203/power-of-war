package com.dreamteam.powerofwar.connection.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.connection.message.CodecDispatcher;
import com.dreamteam.powerofwar.connection.message.Decoder;
import com.dreamteam.powerofwar.connection.message.Encoder;
import com.dreamteam.powerofwar.connection.message.Message;
import com.dreamteam.powerofwar.connection.message.MessageHandler;
import com.dreamteam.powerofwar.connection.message.MessageMappingRegisterer;
import com.dreamteam.powerofwar.connection.message.RegistryMessageDispatcher;
import com.dreamteam.powerofwar.connection.server.handler.PrintTextMessageHandler;
import com.dreamteam.powerofwar.connection.message.session.ChannelSession;
import com.dreamteam.powerofwar.connection.message.type.PrintTextMessage;

@SpringBootApplication(scanBasePackages = {
        "com.dreamteam.powerofwar.game",
        "com.dreamteam.powerofwar.connection.message",
        "com.dreamteam.powerofwar.connection.server"
})
public class ServerTestConnection {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ServerTestConnection.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .run(args);

        ServerConnection server = context.getBean(ServerConnection.class);
        server.start();
    }

    @Bean
    public PrintTextMessageHandler closeConnectionMessageHandler() {
        return new PrintTextMessageHandler();
    }

    @Bean
    RegistryMessageDispatcher registryMessageDispatcher(List<MessageHandler<? extends Message>> handlers) {
        return new RegistryMessageDispatcher(handlers);
    }

    @Bean
    CodecDispatcher codecDispatcher(List<Encoder<?>> encoders, List<Decoder<?>> decoders) {
        return new CodecDispatcher(encoders, decoders);
    }

    @Bean
    MessageMappingRegisterer registerer() {
        MessageMappingRegisterer registerer = new MessageMappingRegisterer();
        registerer.register(1, PrintTextMessage.class);
        return registerer;
    }

    @Bean
    ServerConnection serverConnection(RegistryMessageDispatcher registryMessageDispatcher, CodecDispatcher codecDispatcher, MessageMappingRegisterer registerer) throws IOException {
        return new ServerConnection() {
            @Override
            ChannelSession createChannelSession(SocketChannel socketChannel) {
                return new ChannelSession(socketChannel, registryMessageDispatcher, codecDispatcher, registerer);
            }
        };
    }

}
