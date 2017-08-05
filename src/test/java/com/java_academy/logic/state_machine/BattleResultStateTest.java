package com.java_academy.logic.state_machine;

import com.java_academy.logic.model.Players;
import com.java_academy.logic.state_machine.BattleResultState;
import com.java_academy.logic.state_machine.EndGameState;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Bartlomiej Janik
 * @since 8/1/2017
 */
@Test
public class BattleResultStateTest {

    private BattleResultState battleResult;
    private Players currentPlayer;

    @BeforeTest
    public void setUp(){
        currentPlayer = Players.FIRST_PLAYER;
        battleResult = new BattleResultState(currentPlayer);
    }

    @Test
    public void checkIfItIsEndingState(){
        Assert.assertFalse(battleResult.isEndingState());
    }

    @Test
    public void checkIfNextStateIsEndGame(){
        Assert.assertTrue(battleResult.changeState(null) instanceof EndGameState);
    }

}
