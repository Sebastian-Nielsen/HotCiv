package hotciv.common.distribution;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
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
//		RequestObject requestObject = gson.fromJson(request, RequestObject.class);

		// Create the reply object
		ReplyObject reply;
		reply = new ReplyObject(200,
				gson.toJson(Player.RED));

		// marshall the reply object
		return gson.toJson(reply);
	}
}
