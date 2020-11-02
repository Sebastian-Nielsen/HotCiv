package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.framework.Unit;

import static hotciv.framework.GameConstants.*;

public abstract class UnitImpl implements Unit {

	protected final Player owner;
	protected int attackingStrength;
	protected int defensiveStrength;
	protected int movesLeft;
	protected int moveCount;

	public UnitImpl(Player owner) {
		this.owner = owner;
	}


	public void performAction(GameImpl game, Position pos) {
		throw new RuntimeException(game.getUnitAt(pos).getTypeString() + "has no 'perform action'.");
	}


	public void setMovesLeft(int value) {
		movesLeft = value;
	}

	public int getMovesLeft() {
		return movesLeft;
	}

	public abstract String getTypeString();

	@Override
	public Player getOwner() {
		return owner;
	}

	@Override
	public int getMoveCount() {
		return moveCount;
	}
//
//	public boolean canTraverse(Tile tile) {
//		String tileType = tile.getTypeString();
//		if (tileType.equals(OCEANS) || tileType.equals(MOUNTAINS))
//			return false;
//		if (tileType.equals(DESERT))
//			return type.equals(CARAVAN);
//
//		return true; // Every other tile is walkable by all unit types
//	}


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


	public void resetMovesLeft() {
		movesLeft = moveCount;
	}

}
