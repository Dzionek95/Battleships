package com.java_academy.network;

import com.java_academy.network.socket_provider.ClientSocketProvider;
import com.java_academy.network.socket_provider.core.SocketProvider;
import org.testng.annotations.Test;

import java.net.Socket;

import static org.testng.Assert.assertNotNull;

/**
 * @author Siarhei Shauchenka
 * @since 31.07.17.
 */

@Test
public class ConnectorTest {

    public void creationInstanceTest() {
        Socket socket = new Socket();
        SocketProvider socketProvider = new ClientSocketProvider(socket);
        Connector connector = new Connector(socketProvider);
        assertNotNull(connector);
    }
}
