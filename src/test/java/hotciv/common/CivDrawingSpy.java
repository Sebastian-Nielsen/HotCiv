package hotciv.common;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.ArrayList;
import java.util.List;

public class CivDrawingSpy implements GameObserver {

	private Position tileFocus;
	@Override
	public void tileFocusChangedAt(Position position) {
		tileFocus = position;
	}


	private List<Position> CallsToWorldChangedAt = new ArrayList<>();
	@Override
	public void worldChangedAt(Position pos) {
		CallsToWorldChangedAt.add(pos);
	}



	private Player currentPlayer;
	private int currentAge;
	private int numberOfCallsToTurnEnds = 0;
	@Override
	public void turnEnds(Player nextPlayer, int age) {
		numberOfCallsToTurnEnds++;
		currentPlayer = nextPlayer;
		currentAge = age;
	}


	/* SPY - GETTER METHODS */
	public Position getTileFocus() {
		return tileFocus;
	}
	public List<Position> GetCallsToWorldChangedAt() {
		return CallsToWorldChangedAt;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public int getCurrentAge() {
		return currentAge;
	}
	public int getNumberOfCallsToTurnEnds() {
		return numberOfCallsToTurnEnds;
	}

}
