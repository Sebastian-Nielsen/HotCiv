package hotciv.broker.Invoker.concreteInvokers;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.broker.NameService;
import hotciv.framework.Tile;

import static hotciv.broker.Constants.*;
import static hotciv.broker.Constants.OperationNames.GET_TILE_TYPESTRING;
import static hotciv.framework.GameConstants.OCEANS;

public class TileInvoker implements Invoker {
	private final Gson gson;
	private final NameService nameService;
	private JsonParser jsonParser;

	public TileInvoker(NameService ns) {
		this.nameService = ns;
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
			System.out.println("Debug 4873: " + tile);
			String typeString = (tile == null) ? OCEANS : tile.getTypeString();
			reply = new ReplyObject(200, gson.toJson(typeString));
		} else {
			reply = new ReplyObject(500, gson.toJson("Unknown operation: " + operation));
		}

		return gson.toJson(reply);

	}

	private Tile lookupTile(String objectId) {
		return (Tile) nameService.get(objectId);
	}
}
