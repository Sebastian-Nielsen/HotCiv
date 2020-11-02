package hotciv.common.attackStrategies;

import hotciv.common.GameImpl;
import hotciv.framework.*;

public class CombinedStrengthAttackStrategy implements AttackStrategy {
	private RandomNumberStrategy randomNumberStrategy;
	private final int[][] adjacentDeltaPositions = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};

	public CombinedStrengthAttackStrategy(RandomNumberStrategy randomNumberStrategy) {
		this.randomNumberStrategy = randomNumberStrategy;
	}

	@Override
	public boolean attackUnit(Position from, Position to, GameImpl game) {
		if (hasAttackerWon(from, to, game)) {
			game.popUnitAt(to);
			game.updateUnitPos(from, to);
			return true;
		} else {
			game.popUnitAt(from);
			return false;
		}
	}

	private boolean hasAttackerWon(Position from, Position to, GameImpl game) {
		int attackerStrength = getAttackerStrength(from, game);
		int defenderStrength = getDefenderStrength(to,   game);

		int combinedAttackerStrength = calcCombinedStrength(attackerStrength, from, game);
		int combinedDefenderStrength = calcCombinedStrength(defenderStrength, to,   game);

		return (combinedAttackerStrength * randomNumberStrategy.getRandomSixSidedDieNumber()) >
				(combinedDefenderStrength * randomNumberStrategy.getRandomSixSidedDieNumber());

	}

	private int getAttackerStrength(Position pos, GameImpl game) {
		return game.getUnitAt(pos).getAttackingStrength();
	}

	private int getDefenderStrength(Position pos, GameImpl game) {
		return game.getUnitAt(pos).getDefensiveStrength();
	}

	private int calcCombinedStrength(int baseStrength, Position pos, GameImpl game) {
		return (baseStrength + getFriendlySupport(pos, game)) * getTerrainFactor(pos, game);
	}


	public int getTerrainFactor(Position pos, GameImpl game) {
		if (game.getTileAt(pos).getTypeString().equals("hills"))
			return 2;
		if (game.getTileAt(pos).getTypeString().equals("forest"))
			return 2;
		if (game.getCityAt(pos) != null)
			return 3;
		return 1;
	}

	public int getFriendlySupport(Position pos, GameImpl game) {
		int supportingUnits = 0;
		for (int[] deltaPos : adjacentDeltaPositions) {
			Position adjacentPos = new Position(pos.getRow() + deltaPos[0],
												pos.getColumn() + deltaPos[1]);

			Player unitOwner = game.getUnitAt(pos).getOwner();
			if (isAllyUnitAtPos(adjacentPos, unitOwner, game))
				supportingUnits++;

		}
		return supportingUnits;
	}

	private boolean isAllyUnitAtPos(Position pos, Player owner, GameImpl game) {
		Unit adjacentUnit = game.getUnitAt(pos);
		return game.isUnitAtPos(pos) && adjacentUnit.getOwner() == owner;
	}
}
