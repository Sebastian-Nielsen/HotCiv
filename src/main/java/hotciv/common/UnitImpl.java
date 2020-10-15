package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Unit;

import static hotciv.framework.GameConstants.*;

public class UnitImpl implements Unit {

	private final String type;
	private final Player owner;
	private boolean isFortified;
	private int attackingStrength;
	protected int defensiveStrength;
	private int movesLeft = 1;

	public UnitImpl(String type, Player owner) {
		this.type = type;
		this.owner = owner;
		
		// Set defensiveStrength and attackStrength
		switch (type) {
			case SETTLER:
				this.defensiveStrength = SETTLER_DEFENSIVE_STRENGTH;
				this.attackingStrength = SETTLER_ATTACK_STRENGTH;
				break;
			case ARCHER:
				this.defensiveStrength = ARCHER_DEFENSIVE_STRENGTH;
				this.attackingStrength = ARCHER_ATTACK_STRENGTH;
				break;
			case LEGION:
				this.defensiveStrength = LEGION_DEFENSIVE_STRENGTH;
				this.attackingStrength = LEGION_ATTACK_STRENGTH;
				break;
		}
	}

	public void setMovesLeft(int value) {
		movesLeft = value;
	}

	public int getMovesLeft() {
		return movesLeft;
	}

	@Override
	public String getTypeString() {
		return type;
	}

	@Override
	public Player getOwner() {
		return owner;
	}

	@Override
	public int getMoveCount() {
		return 1;
	}

	@Override
	public int getDefensiveStrength() {
		return defensiveStrength;
	}

	@Override
	public int getAttackingStrength() {
		return attackingStrength;
	}

	public void setDefensiveStrength(int newDef) {
		defensiveStrength = newDef;
	}

	public void setFortified(boolean fortified) {
		isFortified = fortified;
	}

	public boolean isFortified() {
		return isFortified;
	}

	public void resetMovesLeft() {
		movesLeft = 1;
	}
}
