package hotciv.common.distribution;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.*;

import static hotciv.common.distribution.OperationNames.*;

public class HotCivGameInvoker implements Invoker {
	private final Game servant;
	private final Gson gson;
	private JsonParser jsonParser;


	public HotCivGameInvoker(Game servant) {
		this.servant = servant;
		gson = new Gson();
		jsonParser = new JsonParser();
	}

	@Override
	public String handleRequest(String request) {
		// Do the demarshalling
		RequestObject requestObject = gson.fromJson(request, RequestObject.class);
		JsonArray array = jsonParser.parse(requestObject.getPayload()).getAsJsonArray();

		ReplyObject reply;
		Position pos;
		String operation = requestObject.getOperationName();
		switch (operation) {
			case GET_WINNER:
				Player winner = servant.getWinner();
				reply = new ReplyObject(200, gson.toJson(winner));

				break;
			case GET_UNIT_AT:
				// Parameter convention: [0] = Position
				pos = gson.fromJson(array.get(0), Position.class);
				Unit unit = servant.getUnitAt(pos);

				Object[] payload = new Object[]{unit.getTypeString(), unit.getOwner()};

				reply = new ReplyObject(200, gson.toJson(payload));

				break;
			case GET_CITY_AT:
				// Parameter convention: [0] = Position
				pos = gson.fromJson(array.get(0), Position.class);
				City city = servant.getCityAt(pos);

				reply = new ReplyObject(200, gson.toJson(city));


				break;
			case GET_TILE_AT:
				// Parameter convention: [0] = Position
				pos = gson.fromJson(array.get(0), Position.class);
				Tile tile = servant.getTileAt(pos);
				reply = new ReplyObject(200, gson.toJson(tile.getTypeString()));

				break;
			default:
				// Unknown operation
				// TODO: Handle this case
				throw new RuntimeException("Unknown operation");
		}

		// marshall the reply object
		return gson.toJson(reply);
	}
}
