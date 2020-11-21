package hotciv.common.distribution.ClientProxy;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.common.CityImpl;
import hotciv.common.Converter;
import hotciv.common.distribution.OperationNames;
import hotciv.framework.*;


public class GameProxy implements Game, ClientProxy {
	private final Requestor requestor;

	public static final String HOTCIV_OBJECTID = "singleton";

	public GameProxy(Requestor requestor) {
		this.requestor = requestor;
	}

	@Override
	public Tile getTileAt(Position pos) {
		String typeString = requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_TILE_AT,
				String.class, pos);

		return Converter.convertTypestringToTileObject(typeString);
	}

	@Override
	public Unit getUnitAt(Position pos) {
		Object[] args = requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_UNIT_AT,
				Object[].class, pos);

		String unitType   = (String) args[0];
		String typeString = (String) args[1];
		Player owner =  Player.valueOf(typeString);

		return Converter.convertTypeStringToUnitObject(unitType, owner);
	}

	@Override
	public City getCityAt(Position pos) {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_CITY_AT,
				CityImpl.class, pos);
	}

	@Override
	public Player getPlayerInTurn() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_PLAYER_IN_TURN,
				Player.class);
	}

	@Override
	public Player getWinner() {
		// TODO: Add try-with-resource

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
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.MOVE_UNIT,
				Boolean.class, from, to);
	}

	@Override
	public void endOfTurn() {
		requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.END_OF_TURN,
				null);
	}

	@Override
	public void changeWorkForceFocusInCityAt(Position p, String balance) {
		requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.CHANGE_WORKFORCE_FOCUS_IN_CITY_AT,
				null, p, balance);
	}

	@Override
	public void changeProductionInCityAt(Position p, String unitType) {
		requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.CHANGE_PRODUCTION_IN_CITY_AT,
				null, p, unitType);
	}

	@Override
	public void performUnitActionAt(Position p) {
		requestor.sendRequestAndAwaitReply(
			HOTCIV_OBJECTID, OperationNames.PERFORM_UNIT_ACTION_AT,
			null, p);
	}

	@Override
	public void addObserver(GameObserver observer) {

	}

	@Override
	public void setTileFocus(Position position) {

	}
}
