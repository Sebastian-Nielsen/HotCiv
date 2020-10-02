package hotciv.common;

import hotciv.framework.*;

public class CombinedStrengthAttackStrategy implements AttackStrategy {
	private RandomNumberStrategy randomNumberStrategy;
	private final int[][] adjacentDeltaPositions = {{-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}};

	public CombinedStrengthAttackStrategy(RandomNumberStrategy randomNumberStrategy) {
		this.randomNumberStrategy = randomNumberStrategy;
	}

	@Override
	public void attackUnit(Position from, Position to, GameImpl game) {
		int combinedAttackStrength = calcCombinedAttackStrength(from, game);
		int combinedDefenceStrength = calcDefensiveStrength(to, game);
		boolean hasAttackerWon = (combinedAttackStrength * randomNumberStrategy.getRandomNumber()) >
								 (combinedDefenceStrength * randomNumberStrategy.getRandomNumber());
		if (hasAttackerWon) {
			game.popUnitAt(to);
			game.updateUnitPos(from, to);
		} else {
			game.popUnitAt(from);
		}
	}

	private int calcDefensiveStrength(Position pos, GameImpl game) {
		return (game.getUnitAt(pos).getDefensiveStrength() +
									getSupportingStrength(pos, game)) *
									getTileMultiplier(pos, game);
	}

	private int calcCombinedAttackStrength(Position pos, GameImpl game) {
		return (game.getUnitAt(pos).getAttackingStrength() +
									getSupportingStrength(pos, game)) *
									getTileMultiplier(pos, game);
	}

	private int getTileMultiplier(Position pos, GameImpl game) {
		if (game.getTileAt(pos).getTypeString().equals("hills"))
			return 2;
		if (game.getTileAt(pos).getTypeString().equals("forest"))
			return 2;
		if (game.getCityAt(pos) != null)
			return 3;
		return 1;
	}

	private int getSupportingStrength(Position pos, GameImpl game) {
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
