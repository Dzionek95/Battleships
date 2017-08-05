package com.java_academy.network.socket_provider;

import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.model.Players;
import com.java_academy.logic.state_machine.core.OnMessageReceiverListener;
import com.java_academy.logic.tools.BSLog;
import com.java_academy.network.Connector;
import com.java_academy.network.socket_provider.core.SocketProvider;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static org.testng.Assert.assertEquals;

/**
 * @author Siarhei Shauchenka
 * @since 31.07.17
 */
public class ClientSocketProviderIntegrationTest {

    private final static Logger LOGGER = BSLog.getLogger(ClientSocketProviderIntegrationTest.class);
    private final InetSocketAddress CORRECT_ADDRESS = new InetSocketAddress("localhost", 5000);
    private final String TEST_MESSAGE = "test_message";

    @Test
    public void receiveAndSendMessageTest() throws InterruptedException {
        BSLog.info(LOGGER, "----------ClientSocketProviderTest---------------");

        //Create connection and execute createServerSocket
        Connector.getExecutor().execute(this::createServerSocket);
        Thread.sleep(300);

        //Create SocketProvider instance and try to connect to th server and send message
        Socket socket = new Socket();
        SocketProvider provider = new ClientSocketProvider(socket);

        OnMessageReceiverListener messageReceiverListener = messageSupplier -> {
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was received from the server");
            assertEquals(messageSupplier.get(), TEST_MESSAGE);
            provider.sendMessage(new MessageObject(null, TEST_MESSAGE));
        };

        provider.setMessageReceiverListener(messageReceiverListener);
        provider.connect(CORRECT_ADDRESS);
        Thread.sleep(300);
        assertEquals(socket.isConnected(), true);
    }

    private void createServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(CORRECT_ADDRESS, 1);
            connectToClient(serverSocket);
        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
    }

    private void connectToClient(ServerSocket serverSocket) {
        try (Socket client = serverSocket.accept();
             DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(client.getInputStream())) {
            dataOutputStream.writeUTF(TEST_MESSAGE);
            dataOutputStream.flush();
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was sent to the client");
            String input = dataInputStream.readUTF();
            BSLog.info(LOGGER, "echo message: " + input + " was received from the client");
            assertEquals(TEST_MESSAGE, input);
        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
    }
}
