package com.java_academy.logic.model;


import com.java_academy.logic.checkers.NeighbourChecker;
import com.java_academy.logic.tools.BSLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BoardManager {

	private Map<Integer, Cell>  board;
	private Ships ships;
	private NeighbourChecker neighbourChecker;
	private Integer boardXDim;
	
	public BoardManager(Ships ships, Integer boardXDim) {
		this.ships = ships;
		this.boardXDim = boardXDim;
		createEmptyBoard(boardXDim);
		neighbourChecker = new NeighbourChecker(this);
	}
	
	private void createEmptyBoard(Integer boardDim) {
		board = new HashMap<>();
		for(int i = 0; i < boardDim * boardDim; i++) {
			board.put(i, Cell.EMPTY);
		}
	}

	public Map<Integer, Cell> getBoardMap() {
		return board;
	}
	
	public Map<Integer, Boolean> getCellChangesByIndexes(List<Integer> indexes) {
		Map<Integer, Boolean> mapToReturn = new HashMap<>();
		for(Integer index: indexes) {
			if(board.get(index).equals(Cell.SHIP_HITTED)) {
				mapToReturn.put(index, true);
			} else {
				mapToReturn.put(index, false);
			}
		}
		return mapToReturn;
	}
	
	public void shotOnIndex(Integer index) {
		if(board.get(index).equals(Cell.SHIP_ALIVE)) {
			board.put(index, Cell.SHIP_HITTED);
			ships.setShipCellStateByIndex(index);
		} else {
			board.put(index, Cell.MISS);
		}
	}
	
	public void showBoard() {
		int cnt = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n");
		for(Entry<Integer, Cell> element: board.entrySet()) {
			sb.append(element.getValue() + "|");
			cnt++;
			if(cnt % 10 == 0){
				sb.append(System.lineSeparator());
			}
		}
		BSLog.info(BSLog.getLogger(getClass()), sb.toString());
	}

	public void setIndexesOnShip(List<Integer> shipIndexes, Ship ship) {
		for(Integer index: shipIndexes) {
			board.put(index, Cell.SHIP_ALIVE);
			ship.setIndex(index, Cell.SHIP_ALIVE);
		}
	}

	public void markNeighboursForShip(List<Integer> neighbourToMark) {
		for(Integer index: neighbourToMark) {
			board.put(index, Cell.BUSY);
		}
	}
	
	public List<Integer> getNeighboursForPoint(Integer index) {
		return neighbourChecker.getNeighbours(index);
	}
	
	public boolean isNeighbour(Integer startPoint, Integer index) {
		return neighbourChecker.isNeighbour(startPoint, index);
	}
	
	public Integer getBoardXDim() {
		return boardXDim;
	}
}
