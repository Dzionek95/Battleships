package com.java_academy.network.socket_provider.core;

import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.state_machine.core.OnMessageReceiverListener;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public interface SocketProvider {

    boolean connect(InetSocketAddress address);
    void sendMessage(MessageObject messageObject);
    void close(OnSocketCloseListener closeListener);
    void setMessageReceiverListener(OnMessageReceiverListener messageReceiveListener);

}
