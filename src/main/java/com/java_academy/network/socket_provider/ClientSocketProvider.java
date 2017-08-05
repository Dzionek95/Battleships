package com.java_academy.network.socket_provider;

import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.tools.BSLog;
import com.java_academy.logic.tools.I18NResolver;
import com.java_academy.network.input.SocketInputDataProcessor;
import com.java_academy.network.output.SocketOutputDataProcessor;
import com.java_academy.network.output.core.OutputDataProcessor;
import com.java_academy.network.socket_provider.core.AbstractSocketProvider;
import com.java_academy.network.socket_provider.core.OnSocketCloseListener;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public class ClientSocketProvider extends AbstractSocketProvider {

    private final static Logger LOGGER = BSLog.getLogger(ClientSocketProvider.class);

    private Socket mSocket;
    private OutputDataProcessor outputDataProcessor;

    public ClientSocketProvider(Socket socket){
        mSocket = socket;
    }

    @Override
    public boolean connect(InetSocketAddress address) {
        try {
            mSocket.connect(address);
            setInputProcessors(SocketInputDataProcessor::new, mSocket);
            outputDataProcessor = setOutputProcessors(SocketOutputDataProcessor::new, mSocket);
        } catch (IOException e) {
            BSLog.error(LOGGER, I18NResolver.getMsgByKey("CANT_CREATE_CONNECTION", e.getMessage()));
            return false;
        }
        return true;
    }


    @Override
    public void sendMessage(MessageObject messageObject) {
        messageQueue.add(messageObject);
        sendNextMessage();
    }

    @Override
    public void close(OnSocketCloseListener closeListener) {
        try {
            outputDataProcessor.closeSocket();
            closeListener.socketClosed();
        } catch (NullPointerException e){
            BSLog.warn(LOGGER, I18NResolver.getMsgByKey("NULL_POINTER_HANDLE_MESSAGE", e.getMessage()));
        }
    }

    @Override
    protected void sendNextMessage() {
        if (!messageQueue.isEmpty() && canSendMessage){
            canSendMessage = false;
            MessageObject messageObject = messageQueue.poll();
            try {
                outputDataProcessor.sendMessage(messageObject.getMessage());
            } catch (NullPointerException e){
                BSLog.warn(LOGGER, I18NResolver.getMsgByKey("NULL_POINTER_HANDLE_MESSAGE", e.getMessage()));
            }
        }

    }
}
