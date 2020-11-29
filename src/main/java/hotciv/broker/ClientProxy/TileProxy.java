package hotciv.broker.ClientProxy;

import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.Tile;

public class TileProxy implements Tile {
	private final Requestor requestor;
	private final String id;

	public TileProxy(Requestor requestor, String id) {
		this.requestor = requestor;
		this.id = id;
	}

	@Override
	public String getTypeString() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_TILE_TYPESTRING,
				String.class);
	}

	@Override
	public String getId() {
		return id;
	}
}
