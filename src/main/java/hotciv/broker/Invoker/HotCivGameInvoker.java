package hotciv.broker.Invoker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.*;

import static hotciv.broker.OperationNames.*;

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

		ReplyObject reply = new ReplyObject(200, null);;
		Position pos;
		String operation = requestObject.getOperationName();
		switch (operation) {
			case PERFORM_UNIT_ACTION_AT:
				// Parameter convention: [0] = Position
				pos = gson.fromJson(array.get(0), Position.class);

				servant.performUnitActionAt(pos);

				break;
			case CHANGE_PRODUCTION_IN_CITY_AT:
				// Parameter convention: [0] = Position
				// Parameter convention: [1] = untiType
				pos = gson.fromJson(array.get(0), Position.class);
				String unitType = gson.fromJson(array.get(1), String.class);

				servant.changeProductionInCityAt(pos, unitType);

				break;
			case CHANGE_WORKFORCE_FOCUS_IN_CITY_AT:
				// Parameter convention: [0] = Position
				// Parameter convention: [1] = Balance
				pos = gson.fromJson(array.get(0), Position.class);
				String balance = gson.fromJson(array.get(1), String.class);

				servant.changeWorkForceFocusInCityAt(pos, balance);

				break;
			case MOVE_UNIT:
				// Parameter convention: [0] = from-position
				// Parameter convention: [1] = to-position
				Position from = gson.fromJson(array.get(0), Position.class);
				Position to   = gson.fromJson(array.get(1), Position.class);

				boolean isMoveSuccessful = servant.moveUnit(from, to);
				reply = new ReplyObject(200, gson.toJson(isMoveSuccessful));

				break;
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
			case GET_AGE:
				int age = servant.getAge();
				reply = new ReplyObject(200, gson.toJson(age));

				break;
			case GET_PLAYER_IN_TURN:
				Player playerInTurn = servant.getPlayerInTurn();
				reply = new ReplyObject(200, gson.toJson(playerInTurn));

				break;
			case END_OF_TURN:
				servant.endOfTurn();
				reply = new ReplyObject(200, null);

				break;
			default:
				// Unknown operation
				// TODO: Handle this case
				throw new RuntimeException("Unknown operation: " + operation);
		}

		// marshall the reply object
		return gson.toJson(reply);
	}
}
