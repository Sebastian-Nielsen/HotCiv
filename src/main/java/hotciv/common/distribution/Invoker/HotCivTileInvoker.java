package hotciv.common.distribution.Invoker;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.common.concreteTiles.PlainsTile;
import hotciv.framework.Tile;

import static hotciv.common.distribution.OperationNames.GET_TILE_TYPESTRING;

public class HotCivTileInvoker implements Invoker {
	private final Gson gson;
	private JsonParser jsonParser;

	public HotCivTileInvoker() {
		gson = new Gson();
		jsonParser = new JsonParser();
	}

	@Override
	public String handleRequest(String request) {
		// Do the demarshalling
		RequestObject requestObject = gson.fromJson(request, RequestObject.class);
		String objectId = requestObject.getObjectId();

		ReplyObject reply;
		String operation = requestObject.getOperationName();

		Tile tile = lookupTile(objectId);

		if (operation.equals(GET_TILE_TYPESTRING)) {
			String typeString = tile.getTypeString();
			reply = new ReplyObject(200, gson.toJson(typeString));
		} else {
			// Unknown operation
			// TODO: Handle this case
			throw new RuntimeException("Unknown operation: " + operation);
		}

		return gson.toJson(reply);

	}

	private Tile lookupTile(String objectId) {
		return new PlainsTile();
	}
}
