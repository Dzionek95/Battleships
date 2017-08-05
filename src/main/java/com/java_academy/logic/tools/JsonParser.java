package com.java_academy.logic.tools;

import com.google.gson.Gson;
import com.java_academy.logic.json_model.JsonMessage;
import com.java_academy.logic.json_model.MarkedIndexes;
import com.java_academy.logic.json_model.Message;

public class JsonParser {
	
	private static Gson gson = new Gson();
	
	public static String parseMarkedIndexesToJson(MarkedIndexes mi) {
		return gson.toJson(mi);
	}
	
	public static MarkedIndexes parseMarkedIndexesFromJson(String jsonString) {
		return gson.fromJson(jsonString, MarkedIndexes.class);
	}
	
	public static String parseMessageToJson(Message message) {
		return gson.toJson(message);
	}
	
	public static Message parseMessageFromJson(String jsonString) {
		return gson.fromJson(jsonString, Message.class);
	}
	
	public static JsonMessage decide(String message) {
		if(message.contains("{\"dataType\":\"message")) {
			return JsonParser.parseMessageFromJson(message);
		} else {
			return JsonParser.parseMarkedIndexesFromJson(message);
		}
	}
}
