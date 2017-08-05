package com.java_academy.network.input;

import com.java_academy.logic.state_machine.core.OnMessageReceiverListener;
import com.java_academy.logic.tools.BSLog;
import com.java_academy.logic.tools.I18NResolver;
import com.java_academy.network.input.core.InputDataProcessor;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import static com.java_academy.network.socket_provider.core.AbstractSocketProvider.CLOSE_MESSAGE;

import static com.java_academy.network.socket_provider.core.AbstractSocketProvider.CLOSE_MESSAGE;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public class SocketInputDataProcessor implements InputDataProcessor {

    private final static Logger LOGGER = BSLog.getLogger(SocketInputDataProcessor.class);

    private Socket mSocket;
    private OnMessageReceiverListener messageReceiverListener;

    @Override
    public void setSocket(Socket socket) {
        mSocket = socket;
    }

    @Override
    public void messageReceived(String message) {
        try {
            messageReceiverListener.onMessageReceived(() -> message);
        } catch (NullPointerException e) {
            BSLog.warn(LOGGER, I18NResolver.getMsgByKey("RECEIVED_MESSAGE_IGNORED", message));
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
        } catch (IOException | NullPointerException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
    }

    @Override
    public void run() {
        try (DataInputStream dataInputStream = new DataInputStream(mSocket.getInputStream())) {
            while (!Thread.interrupted()) {
                String input = dataInputStream.readUTF();
                if (input.equals(CLOSE_MESSAGE)) {
                    Thread.currentThread().interrupt();
                }
                messageReceived(input);
            }

        } catch (SocketException | EOFException ex) {
            BSLog.warn(LOGGER, I18NResolver.getMsgByKey("SOCKET_IS_UNAVAILABLE"));
        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
        BSLog.info(LOGGER, I18NResolver.getMsgByKey("INPUT_PROCESSOR_TERMINATED"));
    }

}
