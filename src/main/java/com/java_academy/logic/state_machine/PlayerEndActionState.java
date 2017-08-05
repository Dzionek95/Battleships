package com.java_academy.logic.state_machine;

import com.java_academy.logic.json_model.MarkedIndexes;
import com.java_academy.logic.json_model.MessageCreator;
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
        System.out.println(currentPlayer.toString());
        this.currentPlayer = currentPlayer;
        this.markedIndexes = markedIndexes;
    }

    @Override
    public void display(Consumer<MessageObject> displayConsumer) {
    	markedIndexes.setIsMyBoard(false);
    	markedIndexes.setIsNukeAvailable(currentPlayer.getPlayer().canUseNuke());
    	displayConsumer.accept(new MessageObject(currentPlayer, MessageCreator.createJsonMarkedIndexes(markedIndexes)));
		markedIndexes.setIsMyBoard(true);
		markedIndexes.setIsNukeAvailable(currentPlayer.getOpponent().getPlayer().canUseNuke());
		displayConsumer.accept(new MessageObject(currentPlayer.getOpponent(), MessageCreator.createJsonMarkedIndexes(markedIndexes)));
    }

    @Override
    public GameState changeState(String message) {
        hasBeenHit = somethingWasHit(markedIndexes.getMap());
        if(checkIfPlayerWon(currentPlayer)) {
            System.out.println("Changing state into battleresultstate");
            return new BattleResultState(currentPlayer);
        }if(hasBeenHit){
            return new PlayerActionState(currentPlayer);
        } else {
            return new SwitchBlockingBoardState(currentPlayer.getOpponent());
        }
    }

    private boolean checkIfPlayerWon(Players player) {
        System.out.println(player.getOpponent().getPlayer().hasNoFleet());
        return player.getOpponent().getPlayer().hasNoFleet();
    }

    public Boolean somethingWasHit(Map<Integer, Boolean> map) {
        for(Entry<Integer, Boolean> markIndex: map.entrySet()) {
			if(markIndex.getValue().equals(true)) {
				return true;
			}
		}
        return false;
    }
}
