package hotciv.common;

import hotciv.framework.AttackStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;

public class AttackerAlwaysWinsAttackStrategy implements AttackStrategy {
	@Override
	public boolean attackUnit(Position from, Position to, Game game) {
		game.popUnitAt(to);
		game.updateUnitPos(from, to);
		return true;
	}

}
