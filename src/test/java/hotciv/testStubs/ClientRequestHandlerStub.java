package hotciv.testStubs;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;

public class ClientRequestHandlerStub implements ClientRequestHandler {
	private final Invoker invoker;


	public ClientRequestHandlerStub(Invoker invoker) {
		this.invoker = invoker;
	}

	/**
	 * A faked implementation:
	 * Instead of doing all kinds of IPC and connection stuff (send/receive) to the ServerRequestHandler,
	 * we simply call the Invoker instead.
	 * @param request The marshalled request to forward to the ServerRequestHandler ...
	 *                Though, since we are faking it, simple forward the request to the Invoker.
	 * @return A ReplyObject
	 */
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
