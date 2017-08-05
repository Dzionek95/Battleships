package com.java_academy.logic.model;

import com.java_academy.logic.fleet_settings.FleetBuilder;
import com.java_academy.logic.fleet_settings.ShipSetter;
import com.java_academy.logic.fleet_settings.Shipyard;
import com.java_academy.logic.fleet_settings.shipyard_impl.Randomizer;

/**
 * @author Bartlomiej Janik
 * @since 7/31/2017
 */
public class Player {

    private String nickname;
    private Integer nukeCounter;
    private Ships fleet;
    private BoardManager board;

    public Player(String nickname, Integer nukeCounter) {
        super();
        this.nickname = nickname;
        this.nukeCounter = nukeCounter;
    }

    public String getNickname() {
        return nickname;
    }
    public void decrementNukeCounter() {
        nukeCounter--;
    }

    public boolean canUseNuke() {
        return nukeCounter > 0 && isFourMastInFleet();
    }

    private boolean isFourMastInFleet() {
        return fleet.isNukeAvailable();
    }

    public boolean hasNoFleet() {
        return fleet.isEmpty();
    }

    public void createFleet() {
        fleet = FleetBuilder.getNonLocalizedShips();
        board = new BoardManager(fleet, 10);

        ShipSetter fleetSetter = new ShipSetter(board);
        Shipyard rand = new Randomizer(fleet, fleetSetter);
        rand.setFleet();
    }

    public BoardManager getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "Player [nickname=" + nickname + ", nukeCounter=" + nukeCounter + "]";
    }
}
