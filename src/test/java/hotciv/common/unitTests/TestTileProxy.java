package hotciv.common.unitTests;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.ClientProxy.TileProxy;
import hotciv.broker.Invoker.HotCivTileInvoker;
import hotciv.framework.Tile;
import hotciv.testStubs.ClientRequestHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.GameConstants.PLAINS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestTileProxy {

	private Tile tile;

	@BeforeEach
	public void Setup() {
		Invoker invoker = new HotCivTileInvoker();

		ClientRequestHandler crh;
		crh = new ClientRequestHandlerStub(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		tile = new TileProxy(requestor);
	}

	@Test
	public void shouldGetPlainsTypeString() {
		String typeString = tile.getTypeString();
		assertThat(typeString, is(PLAINS));
	}

}
