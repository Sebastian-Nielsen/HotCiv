package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;

import java.util.UUID;

public abstract class UnitImpl implements Unit {

	protected final Player owner;
	private final String id;
	protected int attackingStrength;
	protected int defensiveStrength;
	protected int movesLeft;
	protected int moveCount;

	public UnitImpl(Player owner) {
		this.owner = owner;
		// Create the object ID to bind server and client side
		// Servant-ClientProxy objects together
		this.id = UUID.randomUUID().toString();
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

	public String getId() {
		return id;
	}
}
