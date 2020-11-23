package hotciv.broker.Invoker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.common.CityImpl;
import hotciv.framework.City;
import hotciv.framework.Player;

import static hotciv.broker.OperationNames.*;

public class HotCivCityInvoker implements Invoker {
	private final Gson gson;
	private JsonParser jsonParser;

	public HotCivCityInvoker() {
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

		City city = lookupCity(objectId);

		switch (operation) {
			case GET_CITY_OWNER:
				Player owner = city.getOwner();
				reply = new ReplyObject(200, gson.toJson(owner));

				break;
			case GET_SIZE:
				int size = city.getSize();
				reply = new ReplyObject(200, gson.toJson(size));

				break;
			case GET_TREASURY:
				int treasury = city.getTreasury();
				reply = new ReplyObject(200, gson.toJson(treasury));

				break;
			case GET_PRODUCTION:
				String production = city.getProduction();
				reply = new ReplyObject(200, gson.toJson(production));

				break;
			case GET_WORKFORCE_FOCUS:
				String workForceFocus = city.getWorkforceFocus();
				reply = new ReplyObject(200, gson.toJson(workForceFocus));

				break;
			default:
				// Unknown operation
				// TODO: Handle this case
				throw new RuntimeException("Unknown operation: " + operation);

		}

		// marshall the reply object
		return gson.toJson(reply);
	}

	private City lookupCity(String objectId) {
		return new CityImpl(Player.RED);
	}
}
