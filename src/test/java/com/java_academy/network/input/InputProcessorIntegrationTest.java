package com.java_academy.network.input;

import com.java_academy.logic.tools.BSLog;
import com.java_academy.network.Connector;
import com.java_academy.network.input.core.InputDataProcessor;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

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
 * @since 04.08.17
 */

public class InputProcessorIntegrationTest {

    private final static Logger LOGGER = BSLog.getLogger(InputProcessorIntegrationTest.class);
    private final InetSocketAddress CORRECT_ADDRESS = new InetSocketAddress("localhost", 3000);
    private final String TEST_MESSAGE = "test_message";

    @Test
    public void clientSideTest() throws InterruptedException {
        BSLog.info(LOGGER, "----------InputProcessorIntegrationTest---------------");
        //given
        InputDataProcessor processor = new SocketInputDataProcessor();
        Socket clientSocket = new Socket();

        //when
        // Create server socket and wait for connection from client
        Connector.getExecutor().execute(this::createServerSocket);
        Thread.sleep(300);

        // Create client socket and try to connect to server
        Connector.getExecutor().execute(() -> {
            try {
                createClientSocket(clientSocket, processor);
            } catch (IOException e) {
                BSLog.error(LOGGER, e.getMessage());
            }
        });

        Thread.sleep(300);
        //then
        assertEquals(clientSocket.isClosed(), true);
    }

    //add to the processor socket and message listener and connect to server
    private void createClientSocket(Socket clientSocket, InputDataProcessor processor) throws IOException {
        processor.setSocket(clientSocket);
        processor.setMessageListener(messageSupplier -> {
            assertEquals(messageSupplier.get(), TEST_MESSAGE);
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was received from the server");
        });

        try {
            clientSocket.connect(CORRECT_ADDRESS);
            Connector.getExecutor().execute(processor);
        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
    }

    //Create server socket and wait for the client connection
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

    //wait for client connection and send message
    private void connectToClient(ServerSocket serverSocket) {
        try (Socket client = serverSocket.accept();
             DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream())) {
            dataOutputStream.writeUTF(TEST_MESSAGE);
            dataOutputStream.flush();
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was sent to the server");
        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
    }

}
