package com.java_academy.logic.model;

import java.util.List;

public class Ships {

private List<Ship> ships;
	
	public Ships(List<Ship> ships) {
		this.ships = ships;
	}

	public List<Ship> getFleet() {
		return ships;
	}
	
	boolean isNukeAvailable() {
		for(Ship ship: ships) {
			if(ship.isFourMast() && ship.isAlive()) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		Boolean flag = true;
		for(Ship ship: ships) {
			if(ship.isAlive()) {
				flag = false;
			}
		}
		return flag;
	}
	
	public void setShipCellStateByIndex(Integer index) {
		for(Ship ship: ships) {
			if(ship.containsIndex(index)) {
				ship.setIndex(index, Cell.SHIP_HITTED);
			}
		}
	}
}
