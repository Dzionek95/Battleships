package com.java_academy.network;

import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.state_machine.core.OnMessageReceiverListener;
import com.java_academy.logic.tools.BSLog;
import com.java_academy.logic.tools.I18NResolver;
import com.java_academy.network.socket_provider.core.SocketProvider;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public class Connector {

    private final static Logger LOGGER = BSLog.getLogger(Connector.class);

    private final static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(5);

    private static void terminateExecutor() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)){
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            BSLog.info(LOGGER, I18NResolver.getMsgByKey("TRYING_TO_TERMINATE_EXECUTOR", Thread.currentThread().getName()));
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }

    public synchronized static ScheduledExecutorService getExecutor() {
        return executor;
    }

    private SocketProvider socketProvider;

    public Connector(SocketProvider socketProvider) {
        this.socketProvider = socketProvider;
    }

    public void addMessageReceiverListenerToSocketProvider(OnMessageReceiverListener onMessageReceiverListener){
        this.socketProvider.setMessageReceiverListener(onMessageReceiverListener);
    }

    public boolean connect(InetSocketAddress address) {
       return socketProvider.connect(address);
    }

    public void closeConnection() {
            socketProvider.close(Connector::terminateExecutor);
    }

    public void sendMessage(MessageObject messageObject){
        socketProvider.sendMessage(messageObject);
    }
}
