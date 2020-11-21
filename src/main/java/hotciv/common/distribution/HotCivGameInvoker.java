package hotciv.common.distribution;

import frds.broker.Invoker;
import hotciv.framework.Game;

public class HotCivGameInvoker implements Invoker {
	private final Game servant;

	public HotCivGameInvoker(Game servant) {
		this.servant = servant;
	}

	@Override
	public String handleRequest(String request) {
		return null;
	}
}
