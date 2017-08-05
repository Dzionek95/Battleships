package com.java_academy.logic.attacks;

import com.java_academy.logic.json_model.MarkedIndexes;
import com.java_academy.logic.model.BoardManager;

import java.util.List;
/**
 * @author Bartłomiej Janik
 */
public class NukeAttack implements Attack {

	private final BoardManager board;

    public NukeAttack(BoardManager board) {
		this.board = board;
	}

	@Override
	public MarkedIndexes attack(Integer index) {
		List<Integer> neighbours = board.getNeighboursForPoint(index);
		neighbours.add(index);
		for(Integer shotIndex: neighbours) {
			board.shotOnIndex(shotIndex);
		}
		return new MarkedIndexes("data", board.getCellChangesByIndexes(neighbours));
	}
}