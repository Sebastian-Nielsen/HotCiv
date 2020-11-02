package hotciv.common.concreteUnits;

import hotciv.common.UnitImpl;
import hotciv.framework.Player;

import static hotciv.framework.GameConstants.*;

public class SettlerUnit extends UnitImpl {

	public SettlerUnit(Player owner) {
		super(owner);

		this.defensiveStrength = SETTLER_DEFENSIVE_STRENGTH;
		this.attackingStrength = SETTLER_ATTACK_STRENGTH;
		this.moveCount = SETTLER_TRAVEL_DISTANCE;

		movesLeft = SETTLER_TRAVEL_DISTANCE;
	}

	@Override
	public String getTypeString() {
		return SETTLER;
	}


}
