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
		City city = requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_CITY_AT,
				CityImpl.class, pos);

		return city;
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
