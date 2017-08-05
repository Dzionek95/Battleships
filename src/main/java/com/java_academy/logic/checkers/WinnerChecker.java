package com.java_academy.logic.checkers;

import com.java_academy.logic.model.Player;

public class WinnerChecker {

    public static Boolean isWinner(Player player) {
    	return player.hasNoFleet();
    }
}
