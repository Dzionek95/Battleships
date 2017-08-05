package com.java_academy.network.output;

import com.java_academy.logic.tools.BSLog;
import com.java_academy.network.Connector;
import com.java_academy.network.output.core.OutputDataProcessor;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testng.Assert.assertEquals;

/**
 * @author Siarhei Shauchenka
 * @since 04.08.17
 */
public class OutputProcessorIntegrationTest {

    private final static Logger LOGGER = BSLog.getLogger(OutputProcessorIntegrationTest.class);
    private final InetSocketAddress CORRECT_ADDRESS = new InetSocketAddress("localhost", 4000);
    private final String TEST_MESSAGE = "test_message";

    @Test(priority = 3)
    public void clientSideTest() throws InterruptedException {
        BSLog.info(LOGGER, "----------OutputProcessorIntegrationTest---------------");

        //given
        OutputDataProcessor processor = new SocketOutputDataProcessor();
        Socket clientSocket = new Socket();

        //when Try to create connections
        Connector.getExecutor().execute(this::createServerSocket);
        Thread.sleep(300);

        Connector.getExecutor().execute(() -> connectClientSocket(clientSocket, processor));
        Thread.sleep(300);
    }

    //Connect client socket, init processor and send message
    private void connectClientSocket(Socket clientSocket, OutputDataProcessor processor) {
        try {
            clientSocket.connect(CORRECT_ADDRESS);
            processor.setSocket(clientSocket);
            processor.sendMessage(TEST_MESSAGE);
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was sent to the server");
        } catch (IOException e) {
            BSLog.error(LOGGER, "Can\'t connect to Client");
        }
    }

    //Create server socket and wait for connection from the client
    private void createServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(CORRECT_ADDRESS, 1);
            connectToClient(serverSocket);
        } catch (IOException e) {
            BSLog.error(LOGGER, "Can\'t create server or connect to client");
        }
    }

    //Connect server socket with client
    private void connectToClient(ServerSocket serverSocket) {
        try (Socket client = serverSocket.accept();
             DataInputStream dataInputStream = new DataInputStream(client.getInputStream())) {
            String input = dataInputStream.readUTF();
            assertEquals(input, TEST_MESSAGE);
            BSLog.info(LOGGER, "message: " + TEST_MESSAGE + " was received from the client");
        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
    }
}
