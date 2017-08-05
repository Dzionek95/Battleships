package com.java_academy.logic.model;


public enum Cell {
	
	EMPTY(" * "),
	SHIP_ALIVE(" O "),
    BUSY(" - "),
    SHIP_HITTED(" X "),
    MISS(" @ ");

    private String mark;

    Cell(String mark) {
        this.mark = mark;
    }

    public String toString() {
        return mark;
    }
}
