package hotciv.broker.Invoker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.common.concreteUnits.ArcherUnit;
import hotciv.framework.Player;
import hotciv.framework.Unit;

import static hotciv.broker.OperationNames.*;
import static hotciv.framework.Player.RED;

public class HotCivUnitInvoker implements Invoker {
	private final Gson gson;
	private JsonParser jsonParser;

	public HotCivUnitInvoker() {
		gson = new Gson();
		jsonParser = new JsonParser();
	}

	@Override
	public String handleRequest(String request) {
		// Do the demarshalling
		RequestObject requestObject = gson.fromJson(request, RequestObject.class);
		String objectId = requestObject.getObjectId();
		JsonArray array = jsonParser.parse(requestObject.getPayload()).getAsJsonArray();

		ReplyObject reply = new ReplyObject(200, null);
		String operation = requestObject.getOperationName();

		Unit unit = lookupUnit(objectId);

		switch (operation) {
			case GET_UNIT_OWNER:
				Player owner = unit.getOwner();
				reply = new ReplyObject(200, gson.toJson(owner));

				break;
			case GET_UNIT_TYPESTRING:
				String typestring = unit.getTypeString();
				reply = new ReplyObject(200, gson.toJson(typestring));

				break;
			case GET_MOVE_COUNT:
				int moveCount = unit.getMoveCount();
				reply = new ReplyObject(200, gson.toJson(moveCount));

				break;
			case GET_DEFENSIVE_STRENGTH:
				int defStrengh = unit.getDefensiveStrength();
				reply = new ReplyObject(200, gson.toJson(defStrengh));

				break;
			case GET_ATTACKING_STRENGTH:
				int atkStrength = unit.getAttackingStrength();
				reply = new ReplyObject(200, gson.toJson(atkStrength));

				break;
			default:
				// Unknown operation
				// TODO: Handle this case
				throw new RuntimeException("Unknown operation: " + operation);
		}

		return gson.toJson(reply);

	}

	private Unit lookupUnit(String objectId) {
		return new ArcherUnit(RED);
	}
}