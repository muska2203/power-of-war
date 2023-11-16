package com.dreamteam.powerofwar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.dreamteam.powerofwar.client.ClientApplication;
import com.dreamteam.powerofwar.server.ServerApplication;

public class GameStarter {

    public static void main(String[] args) {
        if (args.length == 1) {
            switch (args[0]) {
                case "server":
                    ServerApplication.startServer();
                    break;
                case "client":
                    runClient();
                    break;
                default:
                    printHelp();
                    break;
            }
        } else {
            printHelp();
        }
    }

    public static void runClient() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter server ip: ");
            String ip = reader.readLine();
            ClientApplication.startClient(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printHelp() {
        System.out.println("This game runner must be used with 1 argument (server, client)");
    }
}
