package hotciv.common;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

public class CivDrawingSpy implements GameObserver {
	private Position tileFocus;

	@Override
	public void tileFocusChangedAt(Position position) {
		tileFocus = position;
	}

	public Position getTileFocus() {
		return tileFocus;
	}


	/*  Not Used  */
	@Override
	public void worldChangedAt(Position pos) {}

	@Override
	public void turnEnds(Player nextPlayer, int age) {}
}
