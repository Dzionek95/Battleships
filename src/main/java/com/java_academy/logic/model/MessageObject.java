package com.java_academy.logic.model;


public class MessageObject {

    private Players player;
    private String message;

    public MessageObject(Players player, String message) {
        this.player = player;
        this.message = message;
    }

    public Players getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }
}
