package com.java_academy.logic.state_machine;

import com.java_academy.logic.json_model.MessageCreator;
import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.model.Players;
import com.java_academy.logic.state_machine.core.GameState;

import java.util.function.Consumer;

/**
 * @author Bartlomiej Janik
 * @since 7/31/2017
 */
public class BattleResultState implements GameState {
	
    private Players winner;

    public BattleResultState(Players winner) {
        System.out.println("W konstruktorze");
        this.winner = winner;
    }

    //is waiting on message that's why we need to click again to get there (I guess same with start of game)
    @Override
    public void display(Consumer<MessageObject> outputConsumer) {
        outputConsumer.accept(new MessageObject(winner, MessageCreator.createJsonMessageByKey("you.win")));
    	outputConsumer.accept(new MessageObject(winner.getOpponent(), MessageCreator.createJsonMessageByKey("you.lose")));
    }

    @Override
    public GameState changeState(String inputMessage) {
        return new EndGameState();
    }

}
