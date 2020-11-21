package hotciv.common.unitTests;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.common.NullObserver;
import hotciv.common.distribution.GameProxy;
import hotciv.common.distribution.HotCivGameInvoker;
import hotciv.framework.*;
import hotciv.testStubs.ClientRequestHandlerStub;
import hotciv.testStubs.StubGame3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestGameProxy {

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


	@Test
	public void shouldGetPlayerInTurnAsBlueAfterEndedTurn() {
		game.endOfTurn();
		Player player = game.getPlayerInTurn();
		assertThat(player, is(Player.BLUE));
	}


	@Test
	public void shouldGetAgeAs4000BC() {
		int age = game.getAge();
		assertThat(age, is(-4000));
	}

	@Test
	public void shouldMoveRedArcher() {
		Position redArcherPos = new Position(1,1);
		Position toPos = new Position(1,2);
		boolean success = game.moveUnit(redArcherPos, toPos);
		assertTrue(success);
	}

	// The changeWorkForceFocusInCityAt method isnt't used in our hotciv implementation,
	// but here is a test for it, in case we implement it in the future
//	@Test
//	public void shouldChangeWorkforceInRedCity() {
//		Position redCityPos = new Position(1,1);
//		game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.productionFocus);
//		assertThat(servant.getCityAt(redCityPos).getWorkforceFocus(), is(GameConstants.productionFocus));
//	}

	@Test
	public void shouldChangeProductionInRedCity() {
		Position redCityPos = new Position(1,1);
		City redCity = game.getCityAt(redCityPos);

		assertThat(redCity.getProduction(), is(GameConstants.ARCHER));
		game.changeProductionInCityAt(redCityPos, GameConstants.LEGION);

		// Retreive red city again to see the change
		redCity = game.getCityAt(redCityPos);
		assertThat(redCity.getProduction(), is(GameConstants.LEGION));
	}


	@Test
	public void shouldPerformActionOnRedArcher() {
		Position blueSettlerPos = new Position( 4, 3);
		assertNull(game.getCityAt(blueSettlerPos));

		game.performUnitActionAt(blueSettlerPos);

		assertThat(game.getCityAt(blueSettlerPos).getOwner(), is(Player.BLUE));
	}







}
