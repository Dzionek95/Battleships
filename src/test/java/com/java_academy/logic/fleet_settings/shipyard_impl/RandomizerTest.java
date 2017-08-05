package com.java_academy.logic.fleet_settings.shipyard_impl;

import com.java_academy.logic.fleet_settings.FleetBuilder;
import com.java_academy.logic.fleet_settings.ShipSetter;
import com.java_academy.logic.model.BoardManager;
import com.java_academy.logic.model.Ships;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by Siarhei Shauchenka on 27.07.17.
 */

@Test
public class RandomizerTest {

	private BoardManager board;
	private Randomizer rand;
	private Ships ships;
	private ShipSetter fleetSetter;
	final static Integer xBoardDim = 10;
	
	@BeforeTest
	public void createRandomizer() {
		ships = FleetBuilder.getNonLocalizedShips();
		board = new BoardManager(ships, 10);
		
		fleetSetter = new ShipSetter(board);
		rand = new Randomizer(ships, fleetSetter);
	}
	
	@Test(timeOut = 1000)
	public void setFleetBelowOneSecond() {
		Randomizer rand = new Randomizer(ships, fleetSetter);
		rand.setFleet();
	}
	
	@Test
	public void randomIntegerInScopeTest10000Times() {
		for(int i = 0; i < 100000 ; i++) {
			assertTrue(rand.randomIntegerInScope(0,99) <= 99);
			assertTrue(rand.randomIntegerInScope(0,99) >= 0);
		}
	}
}
