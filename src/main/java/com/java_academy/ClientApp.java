package com.java_academy;

import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.model.Players;
import com.java_academy.network.Connector;
import com.java_academy.network.socket_provider.ClientSocketProvider;
import com.java_academy.network.socket_provider.core.SocketProvider;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static com.java_academy.network.socket_provider.core.AbstractSocketProvider.CLOSE_MESSAGE;

/**
 * @author Siarhei Shauchenka
 * @since 01.08.17
 *
 * Temporary ClientApp for Connection check
 */
public class ClientApp {

    public static void main(String[] args) {

        Socket socket = new Socket();

        SocketProvider socketProvider = new ClientSocketProvider(socket);

        Connector connector = new Connector(socketProvider);

        connector.addMessageReceiverListenerToSocketProvider(messageSupplier -> System.out.println("message from the server: " + messageSupplier.get()));

        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 3000);

        if (connector.connect(inetSocketAddress)){
            String message = "HELLO from Client";
            System.out.println("Sending message \"" + message + "\" to the server");
            connector.sendMessage(new MessageObject(Players.FIRST_PLAYER, message));

            Connector.getExecutor().schedule(() -> {
                connector.sendMessage(new MessageObject(Players.FIRST_PLAYER, CLOSE_MESSAGE));
                connector.closeConnection();
            }, 4, TimeUnit.SECONDS);

        }

    }
}
