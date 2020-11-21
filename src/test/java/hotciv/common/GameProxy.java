package hotciv.common;

import frds.broker.Requestor;
import hotciv.framework.*;

public class GameProxy implements Game {
	public GameProxy(Requestor requestor) {
	}

	@Override
	public Tile getTileAt(Position p) {
		return null;
	}

	@Override
	public Unit getUnitAt(Position p) {
		return null;
	}

	@Override
	public City getCityAt(Position p) {
		return null;
	}

	@Override
	public Player getPlayerInTurn() {
		return null;
	}

	@Override
	public Player getWinner() {
		return Player.RED;
	}

	@Override
	public int getAge() {
		return 0;
	}

	@Override
	public boolean moveUnit(Position from, Position to) {
		return false;
	}

	@Override
	public void endOfTurn() {

	}

	@Override
	public void changeWorkForceFocusInCityAt(Position p, String balance) {

	}

	@Override
	public void changeProductionInCityAt(Position p, String unitType) {

	}

	@Override
	public void performUnitActionAt(Position p) {

	}

	@Override
	public void addObserver(GameObserver observer) {

	}

	@Override
	public void setTileFocus(Position position) {

	}
}
