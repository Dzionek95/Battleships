package com.java_academy.logic.state_machine;

import com.java_academy.logic.state_machine.EndGameState;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Bartlomiej Janik
 * @since 8/1/2017
 */
@Test
public class EndGameStateTest {

    private EndGameState endGameState;

    @BeforeTest
    public void setUp(){
        endGameState = new EndGameState();
    }

    @Test
    public void checkIfItIsEndingState(){
        Assert.assertTrue(endGameState.isEndingState());
    }

    @Test
    public void checkIfNextStateIsNull(){
        Assert.assertNull(endGameState.changeState(null));
    }
}
