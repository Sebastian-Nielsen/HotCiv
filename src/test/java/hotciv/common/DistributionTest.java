package hotciv.common;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.common.distribution.HotCivGameInvoker;
import hotciv.framework.*;
import hotciv.testStubs.StubGame3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class DistributionTest {

	Game game;
	private StubGame3 servant;

	@BeforeEach
	public void Setup() {
		servant = new StubGame3();
		GameObserver nullObserver = new NullObserver();
		servant.addObserver(nullObserver);

		Invoker invoker = new HotCivGameInvoker(servant);

		ClientRequestHandler crh =
				new ClientRequestHandlerStub(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		game = new GameProxy(requestor);
		game.addObserver(nullObserver);
	}

	@Test
	public void shouldGetRedWinner() {
		servant.setWinner(Player.RED); // method in our StubGame3 for testing
		Player winner = game.getWinner();
		assertThat(winner, is(Player.RED));
	}

	@Test
	public void shouldGetBlueWinner() {
		servant.setWinner(Player.BLUE);
		Player winner = game.getWinner();
		assertThat(winner, is(Player.BLUE));
	}

	@Test
	public void shouldGetMountainTile() {
		Position tilePos = new Position(3,3);
		Tile tile = game.getTileAt(tilePos);
		assertThat(tile.getTypeString(), is(GameConstants.MOUNTAINS));
	}

	@Test
	public void shouldGetRedArcher() {
		Position pos = new Position(2,0);
		Unit RedArcher = game.getUnitAt(pos);
		assertThat(RedArcher.getOwner(), is(Player.RED));
		assertThat(RedArcher.getTypeString(), is(GameConstants.ARCHER));
	}


	@Test
	public void shouldGetRedCity() {
		Position pos = new Position(1,1);
		City redCity = game.getCityAt(pos);
		assertThat(redCity.getOwner(), is(Player.RED));
	}




}
