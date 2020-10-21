package com.dreamteam.powerofwar.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.Codec;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.codec.RegistryCodecDispatcher;
import com.dreamteam.powerofwar.connection.session.ChannelSession;
import com.dreamteam.powerofwar.game.Board;
import com.dreamteam.powerofwar.game.GameProgram;
import com.dreamteam.powerofwar.handler.Dispatcher;
import com.dreamteam.powerofwar.handler.Handler;
import com.dreamteam.powerofwar.handler.RegistryDispatcher;

@SpringBootApplication(scanBasePackages = {"com.dreamteam.powerofwar.server", "com.dreamteam.powerofwar.game"})
public class ServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ServerApplication.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .run(args);

        GameProgram gameProgram = context.getBean(GameProgram.class);
        StateUpdater stateUpdater = context.getBean(StateUpdater.class);
        gameProgram.startGame();
        stateUpdater.start();
    }

    @Bean
    public Board board() {
        return new Board();
    }

    @Bean
    public GameProgram gameProgram(Board board) {
        return new GameProgram(board);
    }

    @Bean
    Dispatcher<Message> registryMessageDispatcher(List<Handler<? extends Message>> handlers) {
        return new RegistryDispatcher<>(handlers);
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
    ServerConnection serverConnection(Dispatcher<Message> registryMessageDispatcher, CodecDispatcher codecDispatcher) throws IOException {
        return new ServerConnection() {
            @Override
            ChannelSession createChannelSession(SocketChannel socketChannel) {
                return new ChannelSession(socketChannel, registryMessageDispatcher, codecDispatcher);
            }
        };
    }
}
