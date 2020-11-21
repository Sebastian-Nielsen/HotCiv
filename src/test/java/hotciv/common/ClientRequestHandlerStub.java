package hotciv.common;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;

public class ClientRequestHandlerStub implements ClientRequestHandler {
	private final Invoker invoker;


	public ClientRequestHandlerStub(Invoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public String sendToServerAndAwaitReply(String request) {
		return invoker.handleRequest(request);
	}

	@Override
	public void setServer(String hostname, int port) {

	}

	@Override
	public void close() {

	}
}
