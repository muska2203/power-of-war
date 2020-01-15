package com.dreamteam.powerofwar.connection.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.connection.ConnectionInfo;
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
        "com.dreamteam.powerofwar.connection.message",
        "com.dreamteam.powerofwar.connection.client"
})
public class ClientTestConnection {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientTestConnection.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .run(args);

        ;
        try (ClientConnection clientConnection = context.getBean(ClientConnection.class)) {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (clientConnection.isOpen()) {
                String word = consoleReader.readLine();
                clientConnection.sendMessage(new PrintTextMessage(word));
            }
        } catch (IOException e) {
            System.out.println("Connection has been refused.");
        }
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
    ClientConnection clientConnection(RegistryMessageDispatcher registryMessageDispatcher, CodecDispatcher codecDispatcher, MessageMappingRegisterer registerer) throws IOException {
        return new ClientConnection(new InetSocketAddress(ConnectionInfo.IP, ConnectionInfo.PORT)) {
            @Override
            ChannelSession createChannelSession(SocketChannel socketChannel) {
                return new ChannelSession(socketChannel, registryMessageDispatcher, codecDispatcher, registerer);
            }
        };
    }
}
