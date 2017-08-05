package com.java_academy.logic.fleet_settings;

import com.java_academy.logic.model.BoardManager;
import com.java_academy.logic.model.Ship;
import com.java_academy.logic.model.Ships;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public class FleetBuilderTest {
	private BoardManager board;
	private ShipSetter fleetSetter;
	final static Integer xBoardDim = 10;
	private Ships ships;
	
	@BeforeTest
	public void createRandomizer() {
		ships = FleetBuilder.getNonLocalizedShips();
		board = new BoardManager(ships, 10);
		fleetSetter = new ShipSetter(board);
	}
	
	@Test
	public void getNextIndexUp() {
		assertEquals(fleetSetter.getNextIndex(20, 0), 10);
	}
	
	@Test
	public void getNextIndexRight() {
		assertEquals(fleetSetter.getNextIndex(0, 1), 1);
	}
	
	@Test
	public void getNextIndexDown() {
		assertEquals(fleetSetter.getNextIndex(11, 2), 21);
	}
	
	@Test
	public void getNextIndexLeft() {
		assertEquals(fleetSetter.getNextIndex(25, 3), 24);
	}
	
	@Test
	public void busyNeighbours() {
		BoardManager board = new BoardManager(ships, 10);
		ShipSetter fleetSetter = new ShipSetter(board);
		fleetSetter.setShip(Arrays.asList(9, 19, 29), new Ship(3));
		
		assertTrue(fleetSetter.busyNeighbours(18));
		assertFalse(fleetSetter.busyNeighbours(20));
	}

	public void beforePointAvailable() {
		BoardManager board = new BoardManager(ships, 10);
		ShipSetter fleetSetter = new ShipSetter(board);

		fleetSetter.setShipOnTheBoard(Arrays.asList(9, 19, 29), new Ship(3));
		fleetSetter.setShipOnTheBoard(Arrays.asList(83, 84, 85, 86), new Ship(4));
		board.showBoard();
	}

	@DataProvider(name = "PointIsAvailable")
	private Object[][] getAvailablePoints(){
		return new Object[][]{
				{4, 4}, //empty space
				{10, 11}, //new line<index close to ship>
				{49, 39}, //close ship vertically
				{48, 38}, //close ship diagonally
				{63, 64} //close ship horizontally
		};
	}
	
	@Test(dataProvider = "PointIsAvailable")
	public void testPointIsAvailable(Integer point, Integer ancestor) {
		beforePointAvailable();
		assertTrue(fleetSetter.pointIsAvailable(point, ancestor));
	}

}
