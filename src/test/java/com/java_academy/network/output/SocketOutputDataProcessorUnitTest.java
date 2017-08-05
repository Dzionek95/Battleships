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
import static org.testng.Assert.assertNotNull;

/**
 * @author Siarhei Shauchenka
 * @since 31.07.17
 */
public class SocketOutputDataProcessorUnitTest {

    @Test(priority = 1)
    public void creationInstanceTest() {
        OutputDataProcessor processor = new SocketOutputDataProcessor();
        assertNotNull(processor);
    }

    @Test(priority = 2)
    public void closeSocketTest() throws IOException {
        OutputDataProcessor processor = new SocketOutputDataProcessor();
        Socket socket = new Socket();
        processor.setSocket(socket);
        processor.closeSocket();
        assertEquals(socket.isClosed(), true);
    }
}
