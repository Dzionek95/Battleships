package com.java_academy.logic.json_model;

public class Message implements JsonMessage {
	
	private String dataType;
	private String message;
	private String player;
	
	public Message(String dataType, String message) {
		this.dataType = dataType;
		this.message = message;
	}
	
	public Message(String dataType, String message, String player) {
		this.dataType = dataType;
		this.message = message;
		this.player = player;
	}

	public String getDataType() {
		return dataType;
	}

	public String getMessage() {
		return message;
	}
	
	public String getPlayer() {
		return player;
	}
}
