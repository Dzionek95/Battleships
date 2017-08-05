package com.java_academy.logic.state_machine.core;

import java.util.function.Supplier;

/**
 * @author Siarhei Shauchenka on 28.07.17.
 * This inteface is responsible for callback from Input data steram
 */
@FunctionalInterface
public interface OnMessageReceiverListener {
    void onMessageReceived(Supplier<String> messageSupplier);
}
