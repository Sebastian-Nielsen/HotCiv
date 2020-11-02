package hotciv.common.concreteUnits;

import hotciv.common.UnitImpl;
import hotciv.framework.Player;

import static hotciv.framework.GameConstants.*;

public class ArcherUnit extends UnitImpl {
	private boolean isFortified;


	public ArcherUnit(Player owner) {
		super(owner);

		this.defensiveStrength = ARCHER_DEFENSIVE_STRENGTH;
		this.attackingStrength = ARCHER_ATTACK_STRENGTH;
		this.moveCount = ARCHER_TRAVEL_DISTANCE;

		movesLeft = ARCHER_TRAVEL_DISTANCE;
	}


	public void setFortified(boolean fortified) {
		isFortified = fortified;
	}

	public boolean isFortified() {
		return isFortified;
	}

	@Override
	public String getTypeString() {
		return ARCHER;
	}
}
