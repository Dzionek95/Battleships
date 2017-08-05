package com.java_academy.network.socket_provider.core;

/**
 * @author Siarhei Shauchenka
 * @since 01.08.17
 */
@FunctionalInterface
public interface OnSocketCloseListener {
    void socketClosed();
}
