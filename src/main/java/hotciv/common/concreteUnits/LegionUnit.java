package hotciv.common.concreteUnits;

import hotciv.common.UnitImpl;
import hotciv.framework.Player;

import static hotciv.framework.GameConstants.*;

public class LegionUnit extends UnitImpl {

	public LegionUnit(Player owner) {
		super(owner);

		this.defensiveStrength = LEGION_DEFENSIVE_STRENGTH;
		this.attackingStrength = LEGION_ATTACK_STRENGTH;
		this.moveCount = LEGION_TRAVEL_DISTANCE;

		movesLeft = LEGION_TRAVEL_DISTANCE;
	}

	@Override
	public String getTypeString() {
		return LEGION;
	}

}
