package com.java_academy.logic.state_machine;

import com.java_academy.logic.jsonModel.MarkedIndexes;
import com.java_academy.logic.jsonModel.MessageCreator;
import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.model.Players;
import com.java_academy.logic.state_machine.core.GameState;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * @author Bartlomiej Janik
 * @since 7/31/2017
 */
public class PlayerEndActionState implements GameState {

    private Players currentPlayer;
    private Boolean hasBeenHit;
    private MarkedIndexes markedIndexes;

    public PlayerEndActionState(Players currentPlayer, MarkedIndexes markedIndexes) {
        this.currentPlayer = currentPlayer;
        this.markedIndexes = markedIndexes;
    }

    @Override
    public void display(Consumer<MessageObject> displayConsumer) {
    	markedIndexes.setIsMyBoard(true);
    	displayConsumer.accept(new MessageObject(currentPlayer, MessageCreator.createJsonMarkedIndexes(markedIndexes)));
		markedIndexes.setIsMyBoard(false);
		displayConsumer.accept(new MessageObject(currentPlayer.getOpponent(), MessageCreator.createJsonMarkedIndexes(markedIndexes)));
    }

    @Override
    public GameState changeState(String message) {
        hasBeenHit = somethingWasHit(markedIndexes.getMap());
        currentPlayer.getPlayer().decrementNukeCounter();

        if(checkIfPlayerWon(currentPlayer)) {
            return new BattleResultState(currentPlayer);
        } else if(hasBeenHit){
            return new PlayerActionState(currentPlayer);
        } else {
            return new PlayerActionState(currentPlayer.getOpponent());
        }
    }

    private boolean checkIfPlayerWon(Players player) {
        return player.getOpponent().getPlayer().hasNoFleet();
    }

    public Boolean somethingWasHit(Map<Integer, Boolean> map) {
    	//TODO use stream like below
    	//return map.entrySet().stream().anyMatch(Map.Entry::getValue);
    	for(Entry<Integer, Boolean> markIndex: map.entrySet()) {
			if(markIndex.getValue().equals(true)) {
				return true;
			}
		}
        return false;
    }
}
