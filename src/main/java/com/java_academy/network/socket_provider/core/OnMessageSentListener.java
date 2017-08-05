package com.java_academy.network.socket_provider.core;

/**
 * @author Siarhei Shauchenka
 * @since 04.08.17
 */
@FunctionalInterface
public interface OnMessageSentListener {

    void onMessageSent();
}
