package com.java_academy.logic.model;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ShipTest {
	
	private Ship fourMastShip;
	private Ship twoMastShip;
	private final static Integer fourMastAmount = 4;
	private final static Integer twoMastAmount = 2;
	
	@BeforeTest
	public void createShips() {
		fourMastShip = new Ship(fourMastAmount);
		fourMastShip.setIndex(0, Cell.SHIP_ALIVE);
		fourMastShip.setIndex(10, Cell.SHIP_ALIVE);
		fourMastShip.setIndex(20, Cell.SHIP_ALIVE);
		fourMastShip.setIndex(30, Cell.SHIP_ALIVE);
		twoMastShip = new Ship(twoMastAmount);
		twoMastShip.setIndex(4, Cell.SHIP_ALIVE);
		twoMastShip.setIndex(5, Cell.SHIP_ALIVE);
	}
	
	@Test
	public void testAmountOfMasts() {
		assertEquals(fourMastShip.getMastAmount(), fourMastAmount);
		assertEquals(twoMastShip.getMastAmount(), twoMastAmount);
	}

	@Test
	public void testIsFourMastShip() {
		assertTrue(fourMastShip.isFourMast());
		assertFalse(twoMastShip.isFourMast());
	}
	
	@Test
	public void containsIndexTest() {
		assertTrue(fourMastShip.containsIndex(0));
		assertTrue(fourMastShip.containsIndex(10));
		assertTrue(fourMastShip.containsIndex(20));
		assertTrue(fourMastShip.containsIndex(30));
		
		assertTrue(twoMastShip.containsIndex(4));
		assertTrue(twoMastShip.containsIndex(5));
		
		assertFalse(fourMastShip.containsIndex(4));
		assertFalse(fourMastShip.containsIndex(21));
		
		assertFalse(twoMastShip.containsIndex(43));
		assertFalse(twoMastShip.containsIndex(0));
	}
	
	@Test
	public void twoMastShipIsDestroyed() {
		twoMastShip.setIndex(4, Cell.SHIP_HITTED);
		twoMastShip.setIndex(5, Cell.SHIP_HITTED);
		
		assertFalse(twoMastShip.isAlive());
	}
}
