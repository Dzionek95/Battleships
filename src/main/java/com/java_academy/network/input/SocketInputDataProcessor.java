package com.java_academy.network.input;

import com.java_academy.logic.state_machine.core.OnMessageReceiverListener;
import com.java_academy.network.input.core.InputDataProcessor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public class SocketInputDataProcessor implements InputDataProcessor {

    private Socket mSocket;
    private OnMessageReceiverListener messageReceiverListener;

    @Override
    public void setSocket(Socket socket) {
        mSocket = socket;
    }

    @Override
    public void messageReceived(String message) {
        if (messageReceiverListener != null){
            messageReceiverListener.onMessageReceived(() -> message);
        }
    }

    @Override
    public void setMessageListener(OnMessageReceiverListener messageListener) {
        this.messageReceiverListener = messageListener;
    }

    @Override
    public void closeSocket() {
        try {
            mSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (DataInputStream dataInputStream = new DataInputStream(mSocket.getInputStream())){
            while (!Thread.interrupted()){
                messageReceived(dataInputStream.readUTF());
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
