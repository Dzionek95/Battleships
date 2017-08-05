package com.java_academy.logic.json_model;

import java.util.Map;

public class MarkedIndexes implements JsonMessage {
	
	private String dataType;
	private Map<Integer, Boolean> map;
	private Boolean isMyBoard;
	private Boolean isNukeAvailable;

	public MarkedIndexes(String dataType, Map<Integer, Boolean> map) {
		this.dataType = dataType;
		this.map = map;
	}

	public String getDataType() {
		return dataType;
	}

	public Map<Integer, Boolean> getMap() {
		return map;
	}
	
	public void setIsMyBoard(Boolean isMyBoard) {
		this.isMyBoard = isMyBoard;
	}

	public Boolean isMyBoard() {
		return isMyBoard;
	}
	
	public Boolean getIsNukeAvailable() {
		return isNukeAvailable;
	}

	public void setIsNukeAvailable(Boolean isNukeAvailable) {
		this.isNukeAvailable = isNukeAvailable;
	}
}
