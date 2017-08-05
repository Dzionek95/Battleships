package com.java_academy.logic.attacks;

import com.java_academy.logic.json_model.MarkedIndexes;
import com.java_academy.logic.model.BoardManager;

import java.util.Collections;
import java.util.List;
/**
 * @author Bartłomiej Janik
 */
public class NormalAttack implements Attack {
	
	private final BoardManager board;
	
	public NormalAttack(BoardManager board) {
		this.board = board;
	}
	
    @Override
    public MarkedIndexes attack(Integer index) {
    	board.shotOnIndex(index);
		List<Integer> indexes = Collections.singletonList(index);
		return new MarkedIndexes("data", board.getCellChangesByIndexes(indexes));
    }
}