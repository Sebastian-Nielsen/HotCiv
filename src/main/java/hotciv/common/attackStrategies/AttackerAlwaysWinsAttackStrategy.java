package hotciv.common.attackStrategies;

import hotciv.common.GameImpl;
import hotciv.framework.AttackStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;

public class AttackerAlwaysWinsAttackStrategy implements AttackStrategy {
	@Override
	public boolean attackUnit(Position from, Position to, GameImpl game) {
		game.popUnitAt(to);
		game.updateUnitPos(from, to);
		return true;
	}

}
