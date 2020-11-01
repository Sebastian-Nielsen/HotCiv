package hotciv.framework;

import hotciv.common.GameImpl;

public interface AttackStrategy {

	boolean attackUnit(Position from, Position to, GameImpl game);
}
