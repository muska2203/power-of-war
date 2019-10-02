package com.dreamteam.powerofwar.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.dreamteam.powerofwar.client.state.GenericMutableState;
import com.dreamteam.powerofwar.client.state.subject.SelectedGameObject;
import com.dreamteam.powerofwar.client.state.subject.SelectedPlayer;
import com.dreamteam.powerofwar.game.GameProgram;

@SpringBootApplication(scanBasePackages = "com.dreamteam.powerofwar")
public class ClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientApplication.class)
                // Disable default headless mode which prevents AWT instantiation
                .headless(false)
                .run(args);

        GameProgram gameProgram = context.getBean(GameProgram.class);
        gameProgram.startGame();
    }

    @Bean
    public GenericMutableState<SelectedPlayer> selectedPlayerState() {
        return new GenericMutableState<>();
    }

    @Bean
    public GenericMutableState<SelectedGameObject> selectedGameObjectState() {
        return new GenericMutableState<>();
    }
}
