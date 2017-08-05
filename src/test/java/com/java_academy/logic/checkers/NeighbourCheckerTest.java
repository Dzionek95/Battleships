package com.java_academy.logic.checkers;

import com.java_academy.logic.fleet_settings.FleetBuilder;
import com.java_academy.logic.model.BoardManager;
import com.java_academy.logic.model.Ships;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class NeighbourCheckerTest {

    private BoardManager board;
    private NeighbourChecker neighbourChecker;

    @DataProvider(name = "neighbourIfBreakLina")
    private Object[][] getNeighbours() {
        return new Object[][]{
                {9, 10},
                {10, 9}
        };
    }

    @DataProvider(name = "isNeighbour")
    private Object[][] isNeighbour() {
        return new Object[][]{
                {23, 12},
                {23, 13},
                {23, 14},
                {23, 14},
                {23, 22},
                {23, 24},
                {23, 24},
                {23, 32},
                {23, 33},
                {23, 34},
        };
    }

    @DataProvider(name = "getEightNeighbours")
    private Object[][] getEightNeighbours() {
        return new Object[][]{
                {15,8},
                {4,8}
        };
    }

    @BeforeTest
    public void createRandomizer() {
        Ships ships = FleetBuilder.getNonLocalizedShips();
        board = new BoardManager(ships, 10);
        neighbourChecker = new NeighbourChecker(board);
    }

    @Test(dataProvider = "neighbourIfBreakLina")
    public void isNeighbourIfBreakLine(int startPoint, int checkedPoint) {
        assertFalse(neighbourChecker.isNeighbour(startPoint, checkedPoint));
    }

    @Test(dataProvider = "isNeighbour")
    public void isNeighbourTest(int startPoint, int checkPoint) {
        assertTrue(neighbourChecker.isNeighbour(startPoint, checkPoint));

    }

    @Test(dataProvider = "getEightNeighbours")
    public void pointHaveEightNeighbours(int point, int expected) {
        assertEquals(neighbourChecker.getNeighboursToCheckForPoint(point).length, expected);
    }
}
