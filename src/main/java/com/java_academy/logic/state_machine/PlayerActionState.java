package com.java_academy.logic.state_machine;

import com.java_academy.logic.attacks.Attack;
import com.java_academy.logic.attacks.NormalAttack;
import com.java_academy.logic.attacks.NukeAttack;
import com.java_academy.logic.json_model.MarkedIndexes;
import com.java_academy.logic.json_model.MessageCreator;
import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.model.Players;
import com.java_academy.logic.state_machine.core.GameState;

import java.util.function.Consumer;

/**
 * @author Bartlomiej Janik
 * @since 7/31/2017
 */
public class PlayerActionState implements GameState {

    private Players currentPlayer;

    public PlayerActionState(Players currentPlayer){
        this.currentPlayer = currentPlayer;
    }
    @Override
    public void display(Consumer<MessageObject> displayConsumer) {
        displayConsumer.accept(new MessageObject(currentPlayer, MessageCreator.createJsonMessageByKey("your.turn")));
        displayConsumer.accept(new MessageObject(currentPlayer.getOpponent(), MessageCreator.createJsonMessageByKey("not.your.turn")));
   }

    @Override
    public GameState changeState(String inputMessage) {
        MarkedIndexes markedIndexes;
        
        if (currentPlayer.getPlayer().canUseNuke() && inputMessage.startsWith("n")) {
            markedIndexes = dropNuke(inputMessage.replace("n", ""));
            currentPlayer.getPlayer().decrementNukeCounter();
        } else {
            markedIndexes = shootOnField(inputMessage);
        }
        
        return new PlayerEndActionState(currentPlayer, markedIndexes);
    }
    
    private MarkedIndexes dropNuke(String inputMessage) {
    	Integer index = Integer.parseInt(inputMessage);
    	Attack attack = new NukeAttack(currentPlayer.getOpponent().getPlayer().getBoard());
    	return attack.attack(index);
    }
    
    private MarkedIndexes shootOnField(String inputMessage) {
        Integer index = Integer.parseInt(inputMessage);
        Attack attack = new NormalAttack(currentPlayer.getOpponent().getPlayer().getBoard());
    	return attack.attack(index);

    }
}
