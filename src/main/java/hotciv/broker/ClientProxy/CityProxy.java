package hotciv.broker.ClientProxy;

import frds.broker.Requestor;
import hotciv.broker.Constants.*;
import hotciv.framework.City;
import hotciv.framework.Player;

public class CityProxy implements City {
	private final Requestor requestor;

	private String id;

	public CityProxy(Requestor requestor, String id) {
		this.requestor = requestor;
		this.id = id;
	}

	@Override
	public Player getOwner() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_CITY_OWNER,
				Player.class);
	}

	@Override
	public int getSize() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_SIZE,
				Integer.class);
	}

	@Override
	public int getTreasury() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_TREASURY,
				Integer.class);
	}

	@Override
	public String getProduction() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_PRODUCTION,
				String.class);
	}

	@Override
	public String getWorkforceFocus() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_WORKFORCE_FOCUS,
				String.class);
	}

	@Override
	public String getId() {
		return id;
	}
}
