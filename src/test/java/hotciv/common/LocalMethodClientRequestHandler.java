package hotciv.common;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;

public class LocalMethodClientRequestHandler implements ClientRequestHandler {
	public LocalMethodClientRequestHandler(Invoker invoker) {
	}

	@Override
	public String sendToServerAndAwaitReply(String request) {
		return null;
	}

	@Override
	public void setServer(String hostname, int port) {

	}

	@Override
	public void close() {

	}
}
