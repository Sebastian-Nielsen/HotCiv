package hotciv.common.distribution;

import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.Player;

public class CityProxy implements City {
	private final Requestor requestor;

	private String HOTCIV_OBJECTID = " idk man ";

	public CityProxy(Requestor requestor) {
		this.requestor = requestor;
	}

	@Override
	public Player getOwner() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_OWNER,
				Player.class);
	}

	@Override
	public int getSize() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_SIZE,
				Integer.class);
	}

	@Override
	public int getTreasury() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_TREASURY,
				Integer.class);
	}

	@Override
	public String getProduction() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_PRODUCTION,
				String.class);
	}

	@Override
	public String getWorkforceFocus() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_WORKFORCE_FOCUS,
				String.class);
	}
}
