package com.java_academy.logic.tools;

import org.testng.annotations.Test;

import com.java_academy.logic.json_model.MarkedIndexes;
import com.java_academy.logic.json_model.Message;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JsonParserTest {
	
	@Test
	public void parseMarkedIndexesToJsonTest() {
		Map<Integer, Boolean> nukeOnZeroField = new HashMap<>();
		nukeOnZeroField.put(0, true);
		nukeOnZeroField.put(1, false);
		nukeOnZeroField.put(10, true);
		nukeOnZeroField.put(11, false);
		MarkedIndexes mi = new MarkedIndexes("data", nukeOnZeroField);
		
		String indexes = JsonParser.parseMarkedIndexesToJson(mi);
		
		assertEquals(indexes, "{\"dataType\":\"data\",\"map\":{\"0\":true,\"1\":false,\"10\":true,\"11\":false}}");
	}
	
	@Test
	public void parseMarkedIndexesFromJsonTest() {
		String jsonString = "{\"map\":{\"0\":true,\"1\":false,\"10\":true,\"11\":false}}";
		assertTrue(JsonParser.parseMarkedIndexesFromJson(jsonString) instanceof MarkedIndexes);
	}
	
	@Test
	public void parseMessageToJsonTest() {
		Message message = new Message("message", "This is message");
		
		String jsonMessage = JsonParser.parseMessageToJson(message);

		assertEquals(jsonMessage, "{\"dataType\":\"message\",\"message\":\"This is message\"}");
	}
	
}
