package hotciv.broker.ClientProxy;

import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.Tile;

public class TileProxy implements Tile {
	private final Requestor requestor;

	private String HOTCIV_OBJECTID = " idk man2 ";

	public TileProxy(Requestor requestor) {
		this.requestor = requestor;
	}

	@Override
	public String getTypeString() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_TILE_TYPESTRING,
				String.class);
	}
}
