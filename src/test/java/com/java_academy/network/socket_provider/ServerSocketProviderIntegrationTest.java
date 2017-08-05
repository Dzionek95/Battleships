package com.java_academy.network.socket_provider;

import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.model.Players;
import com.java_academy.logic.state_machine.core.OnMessageReceiverListener;
import com.java_academy.logic.tools.BSLog;
import com.java_academy.network.Connector;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/**
 * @author Siarhei Shauchenka
 * @since 01.08.17
 */

public class ServerSocketProviderIntegrationTest {

    private final static Logger LOGGER = BSLog.getLogger(ServerSocketProviderIntegrationTest.class);

    private final String HOST = "localhost";
    private final int PORT = 6000;
    private final InetSocketAddress CORRECT_ADDRESS = new InetSocketAddress(HOST, PORT);
    private final String TEST_MESSAGE = "test_message";

    @Test
    public void receiveAndSendMessageTest() throws InterruptedException {
        BSLog.info(LOGGER, "----------ServerSocketProviderIntegrationTest---------------");

        OnMessageReceiverListener messageReceiverListener = messageSupplier -> {
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was received from the client");
            assertEquals(messageSupplier.get(), TEST_MESSAGE);
        };

        Connector.getExecutor().schedule(this::connectToServer, 1, TimeUnit.SECONDS);
        Connector.getExecutor().schedule(this::connectToServer, 2, TimeUnit.SECONDS);

        try {
            ServerSocket serverSocket = new ServerSocket();
            ServerSocketProvider provider = new ServerSocketProvider(serverSocket);
            provider.setMessageReceiverListener(messageReceiverListener);
            provider.connect(CORRECT_ADDRESS);
            assertEquals(serverSocket.isBound(), true);

            provider.sendMessage(new MessageObject(Players.FIRST_PLAYER, TEST_MESSAGE));
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was sent to the client");

        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }

        Thread.sleep(300);
    }
    
    private void connectToServer() {
        try (Socket socket = new Socket(HOST, PORT);
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
            dataOutputStream.writeUTF(TEST_MESSAGE);
            dataOutputStream.flush();
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was sent to the server");
            String input = dataInputStream.readUTF();
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was received from the server");
            assertEquals(input, TEST_MESSAGE);

        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
    }
}
