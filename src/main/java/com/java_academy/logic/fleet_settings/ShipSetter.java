package com.java_academy.logic.fleet_settings;

import com.java_academy.logic.model.BoardManager;
import com.java_academy.logic.model.Cell;
import com.java_academy.logic.model.Ship;
import com.java_academy.logic.tools.BSLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author  Siarhei Shauchenka
 */
public class ShipSetter {

	BoardManager board;
	Map<Integer, Cell>  boardMap;
	private final Integer boardXDim;
	
	public ShipSetter(BoardManager board) {
		this.board = board;
		boardXDim = board.getBoardXDim();
		boardMap = board.getBoardMap();
	}

	/**
	 * Main method of class, return true if ship placement, false if place is taken
	 * @param startPoint - index of field
	 * @param ship - ship to set
	 * @param rand - to find next point of ship
	 * */
	public boolean setIfPossible(int startPoint, Ship ship, Random rand) {
		List<Integer> shipIndexes = new ArrayList<>();

		int cnt = 0;
		int lastIndex = startPoint;
		int nextIndex = startPoint;
		for(int i = 0 ; i < ship.getMastAmount() ; i++) {
			if(pointIsAvailable(nextIndex, lastIndex)) {
				cnt++;
				if(i > 0) {
					lastIndex = nextIndex;
				}
				shipIndexes.add(lastIndex);
				do {
					nextIndex = getNextIndex(nextIndex, rand.nextInt(4));
				} while (shipIndexes.contains(nextIndex));
			}	
		}
		if(cnt == ship.getMastAmount()) {
			setShipOnTheBoard(shipIndexes, ship);
			return true;
		}
		return false;
	}

	void setShipOnTheBoard(List<Integer> shipIndexes, Ship ship) {
		BSLog.info(BSLog.getLogger(getClass()), "Randomizer create a ship: " + shipIndexes);
		setShip(shipIndexes, ship);
		markNeighbours(shipIndexes);
		board.showBoard();
	}
	
	void setShip(List<Integer> shipIndexes, Ship ship) {
		board.setIndexesOnShip(shipIndexes, ship);
	}
	
	/** 
	 * Method to marking neighbours - change CellState on board to busy - Ships cannot touch
	 * */
	void markNeighbours(List<Integer> shipIndexes) {
		List<Integer> neighbourToMark = new ArrayList<>();

		for(Integer index: shipIndexes) {
			for(Integer neighbour: board.getNeighboursForPoint(index)) {
				if(!boardMap.get(neighbour).equals(Cell.SHIP_ALIVE)) {
					neighbourToMark.add(neighbour);
				}
			}
		}
		board.markNeighboursForShip(neighbourToMark);
	}

	/**
	 * Point is available if is on the board, point and neighbours are not taken and 
	 * earlier point is neighbour - new line condition
	 * @return true if we can place ship
	 * */
	boolean pointIsAvailable(Integer point, Integer ancestor) {
		return pointIsOnTheBoard(point) 
				&& pointNeighborhoodIsEmpty(point)
				&& earlierPointIsNeighbor(point, ancestor);
	}

	/**
	 * Checking CellStates of neighbours for point
	 * */
	boolean busyNeighbours(Integer startPoint) {
		List<Integer> neighbours = board.getNeighboursForPoint(startPoint);
		boolean flag = false;
		
		for(Integer neighbour: neighbours) {
			if(boardMap.get(neighbour).equals(Cell.SHIP_ALIVE)) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	private boolean pointIsOnTheBoard(Integer point) {
		return boardMap.containsKey(point);
	}
	
	private boolean pointNeighborhoodIsEmpty(Integer index){
		return boardMap.get(index).equals(Cell.EMPTY) && !busyNeighbours(index);
	}
	
	private boolean earlierPointIsNeighbor(Integer point, Integer ancestor){
		return board.isNeighbour(point, ancestor);
	}

	/**
	 * @return next index in appropriate direction
	 * */
	int getNextIndex(int index, int direction) {
		if(direction == 0) {
			return index - boardXDim;
		} else if (direction == 1){
			return index + 1;
		} else if (direction == 2){
			return index + boardXDim;
		} else {
			return index - 1;
		}
	}
}

