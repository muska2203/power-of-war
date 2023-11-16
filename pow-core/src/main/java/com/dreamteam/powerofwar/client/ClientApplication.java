package com.dreamteam.powerofwar.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.client.action.Action;
import com.dreamteam.powerofwar.client.game.GameContext;
import com.dreamteam.powerofwar.client.state.GenericMutableState;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.connection.ConnectionInfo;
import com.dreamteam.powerofwar.connection.Message;
import com.dreamteam.powerofwar.connection.codec.Codec;
import com.dreamteam.powerofwar.connection.codec.CodecDispatcher;
import com.dreamteam.powerofwar.connection.codec.RegistryCodecDispatcher;
import com.dreamteam.powerofwar.connection.session.ChannelSession;
import com.dreamteam.powerofwar.connection.session.Session;
import com.dreamteam.powerofwar.handler.Dispatcher;
import com.dreamteam.powerofwar.handler.Handler;
import com.dreamteam.powerofwar.handler.RegistryDispatcher;

@SpringBootApplication(scanBasePackages = {"com.dreamteam.powerofwar.client", "com.dreamteam.powerofwar.server.game"})
public class ClientApplication {

    public static void startClient(String ip) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("ip", ip);
        new SpringApplicationBuilder(ClientApplication.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .properties(properties)
                .run();
    }

    @Value("${ip}")
    private String ip;

    @Bean
    public GenericMutableState<SelectedPlayer> selectedPlayerState() {
        return new GenericMutableState<>();
    }

    @Bean
    public GenericMutableState<SelectedGameObject> selectedGameObjectState() {
        return new GenericMutableState<>();
    }

    @Bean
    public Dispatcher<Action> actionDispatcher(List<Handler<? extends Action>> handlers) {
        return new RegistryDispatcher<>(handlers);
    }

    @Bean
    public GameContext gameContext() {
        return new GameContext();
    }

    @Bean
    public Dispatcher<Message> messageDispatcher(List<Handler<? extends Message>> handlers) {
        return new RegistryDispatcher<>(handlers);
    }

    @Bean
    public CodecDispatcher codecDispatcher(List<Codec<?>> codecs) {
        return new RegistryCodecDispatcher(codecs);
    }

    @Bean
    public Session session(CodecDispatcher codecDispatcher) throws IOException {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress(ip, ConnectionInfo.PORT));
        return new ChannelSession(channel, codecDispatcher);
    }

    @Bean
    public ClientConnection clientConnection(Session session, Dispatcher<Message> messageDispatcher) {
        return new ClientConnection(session, messageDispatcher);
    }
}
