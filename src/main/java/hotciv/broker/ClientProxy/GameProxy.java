package hotciv.broker.ClientProxy;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.broker.Constants.*;
import hotciv.framework.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static hotciv.broker.Constants.NULL_ID;


public class GameProxy implements Game, ClientProxy {
	private final Requestor requestor;

	public static final String HOTCIV_OBJECTID = "singleton";
	private List<GameObserver> gameObservers;

	public GameProxy(Requestor requestor) {
		gameObservers = new ArrayList<>();
		this.requestor = requestor;
	}

	@Override
	public Tile getTileAt(Position pos) {
		String id = requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_TILE_AT,
				String.class, pos);

		return (id.equals(NULL_ID)) ? null : new TileProxy(requestor, id);
	}

	@Override
	public Unit getUnitAt(Position pos) {
		String id = requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_UNIT_AT,
				String.class, pos);

		// System.out.println("¤ test 1 > " + id);  // null
		// System.out.println("¤ test 2 > " + String.valueOf(id == null));
		return (id.equals(NULL_ID)) ? null : new UnitProxy(requestor, id);
	}

	@Override
	public City getCityAt(Position pos) {
		String id = requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_CITY_AT,
				String.class, pos);

		return (id.equals(NULL_ID)) ? null : new CityProxy(requestor, id);
	}

	@Override
	public Player getPlayerInTurn() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_PLAYER_IN_TURN,
				Player.class);
	}

	@Override
	public Player getWinner() {
		// Maybe add try-with-resource

		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_WINNER,
				Player.class);
	}

	@Override
	public int getAge() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_AGE,
				Integer.class);
	}

	@Override
	public boolean moveUnit(Position from, Position to) {
		boolean isMoveSuccessful =
			requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.MOVE_UNIT,
				Boolean.class, from, to);

		notifyObservers(o -> o.worldChangedAt(from));
		notifyObservers(o -> o.worldChangedAt(to));

		return isMoveSuccessful;
	}

	@Override
	public void endOfTurn() {
		requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.END_OF_TURN,
				null);

		// Could get an array of values instead of making two seperate calls here
		notifyObservers(o -> o.turnEnds(getPlayerInTurn(), getAge()));

	}

	private void notifyObservers(Consumer<GameObserver> c) { gameObservers.forEach(c); }

	@Override
	public void changeWorkForceFocusInCityAt(Position p, String balance) {
		requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.CHANGE_WORKFORCE_FOCUS_IN_CITY_AT,
				null, p, balance);

		notifyObservers(o -> o.tileFocusChangedAt(p));
	}

	@Override
	public void changeProductionInCityAt(Position p, String unitType) {
		requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.CHANGE_PRODUCTION_IN_CITY_AT,
				null, p, unitType);

		notifyObservers(o -> o.tileFocusChangedAt(p));
	}

	@Override
	public void performUnitActionAt(Position p) {
		requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.PERFORM_UNIT_ACTION_AT,
				null, p);

		notifyObservers(o -> o.worldChangedAt(p));
	}

	@Override
	public void addObserver(GameObserver observer) {
		gameObservers.add(observer);
	}

	@Override
	public void setTileFocus(Position pos) {
		notifyObservers(o -> o.tileFocusChangedAt(pos));
	}
}
