package com.dreamteam.powerofwar.connection.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.connection.message.Message;
import com.dreamteam.powerofwar.connection.message.MessageDispatcher;
import com.dreamteam.powerofwar.connection.message.MessageHandler;
import com.dreamteam.powerofwar.connection.message.RegistryMessageDispatcher;
import com.dreamteam.powerofwar.connection.message.codec.Codec;
import com.dreamteam.powerofwar.connection.message.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.message.codec.RegistryCodecDispatcher;
import com.dreamteam.powerofwar.connection.message.session.ChannelSession;

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
    MessageDispatcher registryMessageDispatcher(List<MessageHandler<? extends Message>> handlers) {
        return new RegistryMessageDispatcher(handlers);
    }

    @Bean
    CodecDispatcher codecDispatcher(List<Codec<?>> codecs) {
        RegistryCodecDispatcher codecDispatcher = new RegistryCodecDispatcher();
        for (Codec<?> codec : codecs) {
            codecDispatcher.register(codec);
        }
        return codecDispatcher;
    }

    @Bean
    ServerConnection serverConnection(MessageDispatcher registryMessageDispatcher, CodecDispatcher codecDispatcher) throws IOException {
        return new ServerConnection() {
            @Override
            ChannelSession createChannelSession(SocketChannel socketChannel) {
                return new ChannelSession(socketChannel, registryMessageDispatcher, codecDispatcher);
            }
        };
    }

}
