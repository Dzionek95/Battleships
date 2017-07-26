package com.java_academy.ligic.model;


import com.java_academy.logic.Players;
import com.java_academy.logic.model.Direction;
import com.java_academy.logic.model.PlayerBoard;
import com.java_academy.logic.model.Ship;
import com.java_academy.logic.model.ShipsType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.util.stream.IntStream;

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


    static void printBoard(PlayerBoard board) {
        IntStream.range(0, 100).forEach(i -> {
            if (i % 10 == 0 && i != 0) {
                System.out.println();
            }
            System.out.print(board.getBoard().get(i).name() + " ");
        });
        System.out.println();
        System.out.println("--------------------------------");
    }

}