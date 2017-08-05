package com.java_academy.logic.state_machine;

import com.java_academy.logic.model.Players;
import com.java_academy.logic.state_machine.PlayerActionState;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Bartlomiej Janik
 * @since 8/1/2017
 */
@Test
public class PlayerActionStateTest {

    private Players players;
    private PlayerActionState playerActionState;

    @BeforeTest
    public void setUp(){
        players = Players.FIRST_PLAYER;
        playerActionState = new PlayerActionState(players);
    }

    @Test
    public void checkIfItIsEndingState(){
        Assert.assertFalse(playerActionState.isEndingState());
    }

}
