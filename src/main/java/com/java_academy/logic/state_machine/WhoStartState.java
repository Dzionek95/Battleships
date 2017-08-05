package com.java_academy.logic.state_machine;

import java.util.function.Consumer;

import com.java_academy.logic.json_model.MessageCreator;
import com.java_academy.logic.model.MessageObject;
import com.java_academy.logic.model.Players;
import com.java_academy.logic.state_machine.core.GameState;

public class WhoStartState implements GameState {

	@Override
	public void display(Consumer<MessageObject> displayConsumer) {
		displayConsumer.accept(new MessageObject(Players.FIRST_PLAYER, MessageCreator.createJsonMessageByKey("who.start")));
        displayConsumer.accept(new MessageObject(Players.SECOND_PLAYER, MessageCreator.createJsonMessageByKey("who.wait")));
	}

	@Override
	public GameState changeState(String message) {
		return new PlayerActionState(Players.FIRST_PLAYER);
	}
}
