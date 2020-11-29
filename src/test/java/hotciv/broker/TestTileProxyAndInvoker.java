package hotciv.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.ClientProxy.TileProxy;
import hotciv.broker.Invoker.RootInvoker;
import hotciv.common.concreteTiles.OceansTile;
import hotciv.common.concreteTiles.PlainsTile;
import hotciv.framework.Tile;
import hotciv.testStubs.ClientRequestHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.GameConstants.OCEANS;
import static hotciv.framework.GameConstants.PLAINS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestTileProxyAndInvoker {

	private Tile plainsTile;
	private Tile oceansTile;


	@BeforeEach
	public void Setup() {
		plainsTile = new PlainsTile();
		oceansTile = new OceansTile();

		// Populate nameService
		NameService ns = new NameServiceImpl();
		ns.put(plainsTile.getId(), plainsTile);
		ns.put(oceansTile.getId(), oceansTile);

		Invoker invoker = new RootInvoker(null, ns);

		ClientRequestHandler crh;
		crh = new ClientRequestHandlerStub(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		plainsTile = new TileProxy(requestor, plainsTile.getId());
		oceansTile = new TileProxy(requestor, oceansTile.getId());
	}

	@Test
	public void shouldGetPlainsTypeString() {
		String typeString = plainsTile.getTypeString();
		assertThat(typeString, is(PLAINS));
	}

	@Test
	public void shouldGetOceansTypeString() {
		String typeString = oceansTile.getTypeString();
		assertThat(typeString, is(OCEANS));
	}


}
