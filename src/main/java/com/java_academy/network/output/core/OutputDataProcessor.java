package com.java_academy.network.output.core;

import com.java_academy.network.core.SocketProcessor;
import com.java_academy.network.socket_provider.core.OnMessageSentListener;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public interface OutputDataProcessor extends SocketProcessor {
    void sendMessage(String message);
    void setOnMessageSentListener(OnMessageSentListener onMessageSentListener);
}
