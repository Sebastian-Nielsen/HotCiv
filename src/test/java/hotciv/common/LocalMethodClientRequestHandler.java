package hotciv.common;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Player;

public class LocalMethodClientRequestHandler implements ClientRequestHandler {
	private final Gson gson;
	private JsonParser jsonParser;

	public LocalMethodClientRequestHandler(Invoker invoker) {
		gson = new Gson();
		jsonParser = new JsonParser();
	}

	@Override
	public String sendToServerAndAwaitReply(String request) {
		// Do the demarshalling
//		RequestObject requestObject = gson.fromJson(request, RequestObject.class);

		// Create the reply object
		ReplyObject reply;
		reply = new ReplyObject(200,
				gson.toJson(Player.RED));

		// marshall the reply object
		return gson.toJson(reply);
//			"{\"Payload\":'RED',\"errorDescription\":'',\"statusCode\":200,\"versionIdentity\":1}";
	}

	@Override
	public void setServer(String hostname, int port) {

	}

	@Override
	public void close() {

	}
}
