package com.java_academy.logic.model;


import com.java_academy.logic.Players;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

@Test
public class PlayerBoardTest {

    public void creationOfInstance(){
        PlayerBoard playerBoard = new PlayerBoard(Players.FIRST_PLAYER, 10);
        assertNotNull(playerBoard);
        assertNotNull(playerBoard.getBoard());
        printBoard(playerBoard);
    }


    @DataProvider(name = "shipsData")
    public Object[][] shipsProvide(){
        PlayerBoard playerBoard = new PlayerBoard(Players.FIRST_PLAYER, 10);
        return new Object[][]{
                {playerBoard, new Ship(ShipsType.THREE_CELLS).setShipPosition(2, Direction.HORIZONTAL), 1},
                {playerBoard, new Ship(ShipsType.THREE_CELLS).setShipPosition(20, Direction.VERTICAL), 2}
        };
    }

    @Test(dataProvider = "shipsData")
    public void setShipsOnTheBoardTest(PlayerBoard playerBoard, Ship ship, int testCase){
        playerBoard.addShip(ship);
        System.out.println("testCase = [" + testCase + "]");
        printBoard(playerBoard);
    }


    static void printBoard(final PlayerBoard board) {
        board.getBoard().forEach((key, value) -> {
            if (key % 10 == 0 && key != 0) {
                System.out.println();
            }
            System.out.print(value.getMark() + " ");
        });

        System.out.println();
        System.out.println("--------------------------------");
    }

}
