package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.framework.Unit;

import static hotciv.framework.GameConstants.*;

public class UnitImpl implements Unit {

	private final String type;
	private final Player owner;
	private boolean isFortified;
	private int attackingStrength;
	protected int defensiveStrength;
	private int movesLeft;
	private int moveCount;

	public UnitImpl(String type, Player owner) {
		this.type = type;
		this.owner = owner;

		// Set defensiveStrength, attackStrength and moveCount
		switch (type) {
			case SETTLER:
				this.defensiveStrength = SETTLER_DEFENSIVE_STRENGTH;
				this.attackingStrength = SETTLER_ATTACK_STRENGTH;
				this.moveCount = SETTLER_TRAVEL_DISTANCE;
				break;
			case ARCHER:
				this.defensiveStrength = ARCHER_DEFENSIVE_STRENGTH;
				this.attackingStrength = ARCHER_ATTACK_STRENGTH;
				this.moveCount = ARCHER_TRAVEL_DISTANCE;
				break;
			case LEGION:
				this.defensiveStrength = LEGION_DEFENSIVE_STRENGTH;
				this.attackingStrength = LEGION_ATTACK_STRENGTH;
				this.moveCount = LEGION_TRAVEL_DISTANCE;
				break;
			case CARAVAN:
				this.defensiveStrength = CARAVAN_DEFENSIVE_STRENGTH;
				this.attackingStrength = CARAVAN_ATTACK_STRENGTH;
				this.moveCount = CARAVAN_TRAVEL_DISTANCE;
				break;
		}

		// Init moves left
		movesLeft = getMoveCount();
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
		return moveCount;
	}

	public boolean canTraverse(Tile tile) {
		String tileType = tile.getTypeString();
		if (tileType.equals(OCEANS) || tileType.equals(MOUNTAINS))
			return false;
		if (tileType.equals(DESERT))
			return type.equals(CARAVAN);

		return true; // Every other tile is walkable by all unit types
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
		movesLeft = getMoveCount();
	}

	public void performCaravanAction(GameImpl game, Position pos) {
		CityImpl city = (CityImpl) game.getCityAt(pos);
		boolean isCaravanInCity = city != null;
		if (isCaravanInCity) {
			city.setSize(city.getSize() + CARAVAN_SIZE_ACTION_INCREASE);
			game.popUnitAt(pos);
		}

	}
}
