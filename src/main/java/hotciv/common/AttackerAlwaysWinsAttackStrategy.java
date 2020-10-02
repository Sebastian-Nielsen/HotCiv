package hotciv.common;

import hotciv.framework.AttackStrategy;
import hotciv.framework.Position;

public class AttackerAlwaysWinsAttackStrategy implements AttackStrategy {
	@Override
	public void attackUnit(Position from, Position to, GameImpl game) {
		game.popUnitAt(to);
		game.updateUnitPos(from, to);
	}
}
