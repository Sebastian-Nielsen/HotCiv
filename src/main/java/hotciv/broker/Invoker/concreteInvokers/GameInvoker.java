package hotciv.broker.Invoker.concreteInvokers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.broker.NameService;
import hotciv.framework.*;

import static hotciv.broker.OperationNames.*;

public class GameInvoker implements Invoker {
	private final Game servant;
	private final Gson gson;
	private final NameService nameService;
	private JsonParser jsonParser;


	public GameInvoker(Game servant, NameService nameService) {
		this.nameService = nameService;
		this.servant = servant;
		gson = new Gson();
		jsonParser = new JsonParser();
	}

	@Override
	public String handleRequest(String request) {
		// Do the demarshalling
		RequestObject requestObject = gson.fromJson(request, RequestObject.class);
		JsonArray array = jsonParser.parse(requestObject.getPayload()).getAsJsonArray();

		ReplyObject reply = new ReplyObject(200, "asdf");

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

				// If the returned unit isn't null, then set the payload to be the unit.
				// Else the default ReplyObject is used.
				if (unit != null) {
					// In order for the client to refer to a given unit on the server side,
					// we store an (id, unit) pair, so we know which unit the client is refering to
					nameService.put(unit.getId(), unit);
					reply = new ReplyObject(200, gson.toJson(unit.getId()));
				}

				break;
			case GET_CITY_AT:
				// Parameter convention: [0] = Position
				pos = gson.fromJson(array.get(0), Position.class);
				City city = servant.getCityAt(pos);

				if (city != null) {
					nameService.put(city.getId(), city);
					reply = new ReplyObject(200, gson.toJson(city.getId()));
				}

				break;
			case GET_TILE_AT:
				// Parameter convention: [0] = Position
				pos = gson.fromJson(array.get(0), Position.class);
				Tile tile = servant.getTileAt(pos);

				if (tile != null) {
					nameService.put(tile.getId(), tile);
					reply = new ReplyObject(200, gson.toJson(tile.getId()));
				}

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

				break;
			default:
				// Maybe handle this case

				// Unknown operation
				throw new RuntimeException("Unknown operation: " + operation);
		}

		// marshall the reply object
		return gson.toJson(reply);
	}
}
