package com.java_academy;

import com.java_academy.logic.Game;
import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.model.Players;
import com.java_academy.logic.tools.BSLog;
import com.java_academy.network.Connector;
import com.java_academy.network.socket_provider.ServerSocketProvider;
import com.java_academy.network.socket_provider.core.SocketProvider;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.function.Consumer;

import static com.java_academy.network.socket_provider.core.AbstractSocketProvider.CLOSE_MESSAGE;

/**
 * @author Siarhei Shauchenka
 * @since 01.08.17
 * <p>
 * Temporary Server Application for Connection checking
 */
public class ServerApp {

    private final static Logger LOGGER = BSLog.getLogger(ServerApp.class);
    public static Connector connector;
    public static void main(String[] args) {
        ServerApp serverApp = new ServerApp();
        serverApp.start();
    }

    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            SocketProvider socketProvider = new ServerSocketProvider(serverSocket);
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 4000);
            connector = new Connector(socketProvider);
            BSLog.info(LOGGER, "Server is up and running");
            Game game = new Game(connector::sendMessage);
            connector.addMessageReceiverListenerToSocketProvider(game);
            if (connector.connect(inetSocketAddress)) {
                game.startGame();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
