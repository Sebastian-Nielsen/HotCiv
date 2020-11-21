package hotciv.common;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.common.distribution.HotCivGameInvoker;
import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.testStubs.StubGame3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class DistributionTest {

	Game game;
	Requestor requestor;
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
	public void shouldHaveRedWinner() {
		servant.setWinner(Player.RED); // method in our StubGame3 for testing
		Player winner = game.getWinner();
		assertThat(winner, is(Player.RED));

//		Request "{\"operationName\":\"hotciv-get-winner\",\"payload\":\"[]\",\"objectId\":\"singleton\",\"versionIdentity\":1}"
//		Reply	"{\"Payload\":\"[RED]\",\"errorDescription\":\"\",\"statusCode\":\"200\",\"versionIdentity\":1}"


	}

	@Test
	public void shouldHaveBlueWinner() {
		servant.setWinner(Player.BLUE);
		Player winner = game.getWinner();
		assertThat(winner, is(Player.BLUE));
	}


}
