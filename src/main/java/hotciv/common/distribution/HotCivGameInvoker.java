package hotciv.common.distribution;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.Game;
import hotciv.framework.Player;

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
//		JsonArray array = jsonParser.parse(requestObject.getPayload()).getAsJsonArray();

		// Call the servant
		Player winner = servant.getWinner();

		// Create the reply object
		ReplyObject reply;
		reply = new ReplyObject(200,
				gson.toJson(winner));

		// marshall the reply object
		return gson.toJson(reply);
	}
}
