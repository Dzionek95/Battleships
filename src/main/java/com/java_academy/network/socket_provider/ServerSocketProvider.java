package com.java_academy.network.socket_provider;

import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.tools.BSLog;
import com.java_academy.logic.tools.I18NResolver;
import com.java_academy.network.input.SocketInputDataProcessor;
import com.java_academy.network.output.SocketOutputDataProcessor;
import com.java_academy.network.output.core.OutputDataProcessor;
import com.java_academy.network.socket_provider.core.AbstractSocketProvider;
import com.java_academy.network.socket_provider.core.OnMessageSentListener;
import com.java_academy.network.socket_provider.core.OnSocketCloseListener;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.java_academy.logic.model.Players.FIRST_PLAYER;
import static com.java_academy.logic.model.Players.SECOND_PLAYER;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public class ServerSocketProvider extends AbstractSocketProvider{

    private final static Logger LOGGER = BSLog.getLogger(ServerSocketProvider.class);

    private ServerSocket serverSocket;
    private OutputDataProcessor firstPlayerOutputProcessor;
    private OutputDataProcessor secondPlayerOutputProcessor;

    public ServerSocketProvider(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    protected void sendNextMessage(){
        if (!messageQueue.isEmpty() && canSendMessage){
            canSendMessage = false;
            MessageObject messageObject = messageQueue.poll();
            switch (messageObject.getPlayer()){
                case FIRST_PLAYER:
                    firstPlayerOutputProcessor.sendMessage(messageObject.getMessage());
                    break;
                case SECOND_PLAYER:
                    secondPlayerOutputProcessor.sendMessage(messageObject.getMessage());
                    break;
            }
        }
    }

    @Override
    public boolean connect(InetSocketAddress address) {
        try {
            int BACKLOG = 2;
            serverSocket.bind(address, BACKLOG);
            for (int i = 0; i < BACKLOG; i++) {
                Socket socket = serverSocket.accept();
                setInputProcessors(SocketInputDataProcessor::new, socket);
                switch (i){
                    case 0:
                        firstPlayerOutputProcessor = setOutputProcessors(SocketOutputDataProcessor::new, socket);
                        break;
                    case 1:
                        secondPlayerOutputProcessor = setOutputProcessors(SocketOutputDataProcessor::new, socket);
                        break;
                }
            }
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
        firstPlayerOutputProcessor.closeSocket();
        secondPlayerOutputProcessor.closeSocket();
        try {
            closeListener.socketClosed();
        } catch (NullPointerException e){
            BSLog.warn(LOGGER, I18NResolver.getMsgByKey("NULL_POINTER_HANDLE_MESSAGE", e.getMessage()));
        }
    }
}
