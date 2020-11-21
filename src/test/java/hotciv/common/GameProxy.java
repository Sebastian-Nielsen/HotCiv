package hotciv.common;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.common.distribution.OperationNames;
import hotciv.framework.*;



public class GameProxy implements Game, ClientProxy {
	private final Requestor requestor;

	public static final String HOTCIV_OBJECTID = "singleton";

	public GameProxy(Requestor requestor) {
		this.requestor = requestor;
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
		// TODO: Add try-with-resource

		Player winner = requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_WINNER,
				Player.class);

		return winner;
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
