package com.java_academy.logic.fleet_settings.shipyard_impl;

import com.java_academy.logic.fleet_settings.ShipSetter;
import com.java_academy.logic.fleet_settings.Shipyard;
import com.java_academy.logic.model.Ship;
import com.java_academy.logic.model.Ships;

import java.util.Random;

/**
 * Created by Siarhei Shauchenka on 27.07.17.
 */
public class Randomizer implements Shipyard {

	private Ships ships;
	private Random rand = new Random();
	private ShipSetter fleetSetter;
	
	public Randomizer(Ships ships, ShipSetter fleetSetter) {
		this.ships = ships;
		this.fleetSetter = fleetSetter;
	}
	
	/**
	 * All ships are placing on board from random point and random direction
	 * */
	public void setFleet() {
		for(Ship ship: ships.getFleet()) {
			int startPoint;
			do {
				startPoint = findStartPoint();
			} while (!fleetSetter.setIfPossible(startPoint, ship, rand));
		}
	}

	private int findStartPoint() {
		return randomIntegerInScope(0, 99);
	}
	
	Integer randomIntegerInScope(int lowerRange, int upperRange) {
		return rand.nextInt(upperRange - lowerRange + 1) + lowerRange;
	}
	
	Boolean randomDirection() {
		return rand.nextBoolean();
	}


}
