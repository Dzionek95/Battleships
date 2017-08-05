package com.java_academy.logic.checkers;

import com.java_academy.logic.model.BoardManager;
import com.java_academy.logic.model.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class for checking neighborhood for point
 * */
public class NeighbourChecker {
	
	private final Map<Integer, Cell>  boardMap;
	private final int boardXDim;

	public NeighbourChecker(BoardManager board) {
		this.boardXDim = board.getBoardXDim();
		boardMap = board.getBoardMap();
	}
	
	/**
	 * get list of neighbours for point
	 * */
	public List<Integer> getNeighbours(Integer startPoint) {
		List<Integer> neighbours = new ArrayList<>();

		for(Integer index: getNeighboursToCheckForPoint(startPoint)) {
			if(isNeighbour(startPoint, index) && boardMap.containsKey(index)) {
				neighbours.add(index);
			}
		}
		return neighbours;
	}

	public boolean isNeighbour(Integer startPoint, Integer checkedPoint) {
		if(startPoint % boardXDim == boardXDim - 1) { // last column in line
			if(checkedPoint - startPoint == 1 || startPoint - checkedPoint == boardXDim - 1 || checkedPoint - startPoint == boardXDim + 1) {
				return false;
			}
		}
		if(startPoint % boardXDim == 0) { //first column in line
			if(startPoint - checkedPoint == 1 || checkedPoint - startPoint == boardXDim - 1 || startPoint - checkedPoint == boardXDim + 1) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * List of points on the board which could be a neighbour for player
	 * */
	Integer[] getNeighboursToCheckForPoint(Integer point) {
		return new Integer[]{point - (boardXDim + 1), point - boardXDim, point - (boardXDim - 1),
				   point - 1, point + 1,
				   point + (boardXDim - 1), point + boardXDim, point + boardXDim + 1};
	}
}
