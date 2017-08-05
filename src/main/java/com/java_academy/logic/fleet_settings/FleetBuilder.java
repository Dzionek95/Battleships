package com.java_academy.logic.fleet_settings;

import com.java_academy.logic.model.Ship;
import com.java_academy.logic.model.Ships;

import java.util.ArrayList;
import java.util.List;

public class FleetBuilder {

	public static Ships getNonLocalizedShips() {

		List<Ship> ships = new ArrayList<>();
    
		Ship mast4 = new Ship(4);
		Ship mast3_1 = new Ship(3);
		Ship mast3_2 = new Ship(3);
		Ship mast2_1 = new Ship(2);
		Ship mast2_2 = new Ship(2);
		Ship mast2_3 = new Ship(2);
		Ship mast1_1 = new Ship(1);
		Ship mast1_2 = new Ship(1);
		Ship mast1_3 = new Ship(1);
		Ship mast1_4 = new Ship(1);
		
		ships.add(mast4);
		ships.add(mast3_1);
		ships.add(mast3_2);
		ships.add(mast2_1);
		ships.add(mast2_2);
		ships.add(mast2_3);
		ships.add(mast1_1);
		ships.add(mast1_2);
		ships.add(mast1_3);
		ships.add(mast1_4);
		
		return new Ships(ships);
	}
}
