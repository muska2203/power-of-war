package com.dreamteam.powerofwar.connection.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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
}
