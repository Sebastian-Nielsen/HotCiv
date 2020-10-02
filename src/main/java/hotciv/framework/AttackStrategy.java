package hotciv.framework;

import hotciv.common.GameImpl;

public interface AttackStrategy {

	void attackUnit(Position from, Position to, GameImpl game);
}
