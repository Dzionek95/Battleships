package com.java_academy.network.output;

import com.java_academy.logic.tools.BSLog;
import com.java_academy.logic.tools.I18NResolver;
import com.java_academy.network.Connector;
import com.java_academy.network.output.core.OutputDataProcessor;
import com.java_academy.network.socket_provider.core.OnMessageSentListener;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public class SocketOutputDataProcessor implements OutputDataProcessor {

    private final static Logger LOGGER = BSLog.getLogger(SocketOutputDataProcessor.class);


    private DataOutputStream dataOutputStream;
    private Socket mSocket;
    private String mMessage;
    private OnMessageSentListener onMessageSentListener;

    @Override
    public void sendMessage(String message) {
        if (message != null && !message.isEmpty() && !mSocket.isClosed() && dataOutputStream != null) {
            mMessage = message;
            Connector.getExecutor().execute(this);
        } else {
            BSLog.warn(LOGGER, I18NResolver.getMsgByKey("CANT_SEND_MESSAGE"));
        }
    }

    @Override
    public void setOnMessageSentListener(OnMessageSentListener onMessageSentListener) {
        this.onMessageSentListener = onMessageSentListener;
    }

    @Override
    public void setSocket(Socket socket) throws IOException {
        mSocket = socket;
        if (socket.isConnected() && !socket.isClosed()) {
                dataOutputStream = new DataOutputStream(mSocket.getOutputStream());
        } else {
            BSLog.warn(LOGGER, I18NResolver.getMsgByKey("SOCKET_IS_UNAVAILABLE"));
        }
    }

    @Override
    public void closeSocket() {
        try {
            if (mSocket != null) {
                mSocket.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        } catch (IOException e) {
            BSLog.error(LOGGER, e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            dataOutputStream.writeUTF(mMessage);
            dataOutputStream.flush();
            onMessageSentListener.onMessageSent();
        } catch (IOException | NullPointerException e) {
            BSLog.error(LOGGER, e.getCause().getMessage());
        }
    }
}
