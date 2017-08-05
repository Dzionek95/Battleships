package com.java_academy.network.input.core;

import com.java_academy.logic.state_machine.core.OnMessageReceiverListener;
import com.java_academy.network.core.SocketProcessor;

/**
 * Created by Siarhei Shauchenka on 28.07.17.
 */
public interface InputDataProcessor extends SocketProcessor {

    void messageReceived(String message);
    void setMessageListener(OnMessageReceiverListener messageListener);

}
