package com.java_academy.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements AutoCloseable{

    private String host;
    private Integer port;
    private Socket socket;

    private Client(String host, Integer port) throws UnknownHostException, IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
    }

    public static Client getClient(String host, Integer port) throws UnknownHostException, IOException {
        return new Client(host, port);
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }

}
