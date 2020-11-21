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

	@BeforeEach
	public void Setup() {
		Game servant = new StubGame3();
		GameObserver nullObserver = new NullObserver();
		servant.addObserver(nullObserver);

		Invoker invoker = new HotCivGameInvoker(servant);

		ClientRequestHandler crh =
				new LocalMethodClientRequestHandler(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		game = new GameProxy(requestor);
		game.addObserver(nullObserver);
	}

	@Test
	public void shouldHaveWinner() {
		Player winner = game.getWinner();
		assertThat(winner, is(Player.RED));
	}


}
