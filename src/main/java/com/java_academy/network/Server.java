package com.java_academy.network;

import com.java_academy.network.InputOutputStreams.DataReader;
import com.java_academy.network.InputOutputStreams.DataWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements AutoCloseable {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataWriter writer;
    private DataReader reader;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    Socket acceptClient() throws IOException {
        return socket = serverSocket.accept();
    }


    @Override
    public void close() throws Exception {
        socket.close();
        serverSocket.close();
    }

}
