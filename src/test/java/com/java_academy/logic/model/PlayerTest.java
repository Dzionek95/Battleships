package com.java_academy.logic.model;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PlayerTest {
	
	private Player player;
	private int nukeCounter = 3;

	@BeforeTest
	public void createPlayer() {
		player = new Player("first", nukeCounter);
		player.createFleet();
	}
	
	@Test
	public void testPlayerCanUseNuke() {
		assertTrue(player.canUseNuke());
	}
	
	@Test
	public void testPlayerCannotUseNukeIfNukeCounterIsZero() {
		player.decrementNukeCounter();
		player.decrementNukeCounter();
		player.decrementNukeCounter();
		assertFalse(player.canUseNuke());
	}
	
	@Test
	public void getBoardTest() {
		player = new Player("first", nukeCounter);
		assertNull(player.getBoard());
		player.createFleet();
		assertNotNull(player.getBoard());
	}
	
	@Test
	public void getNicknameTest() {
		assertEquals(player.getNickname(), "first");
	}
}
