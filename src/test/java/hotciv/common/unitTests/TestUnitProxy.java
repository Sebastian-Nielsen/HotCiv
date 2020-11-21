package hotciv.common.unitTests;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.common.distribution.Invoker.HotCivUnitInvoker;
import hotciv.common.distribution.ClientProxy.UnitProxy;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.testStubs.ClientRequestHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.GameConstants.ARCHER;
import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestUnitProxy {

	private Unit unit;

	@BeforeEach
	public void Setup() {
		Invoker invoker = new HotCivUnitInvoker();

		ClientRequestHandler crh;
		crh = new ClientRequestHandlerStub(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		unit = new UnitProxy(requestor);
	}

	@Test
	public void shouldGetRedOwner() {
		Player owner = unit.getOwner();
		assertThat(owner, is(RED));
	}

	@Test
	public void shouldGetArcherTypestring() {
		String owner = unit.getTypeString();
		assertThat(owner, is(ARCHER));
	}

	@Test
	public void shouldGetMoveCountOne() {
		int moveCount = unit.getMoveCount();
		assertThat(moveCount, is(1));
	}

	@Test
	public void shouldGetDefensiveStrengthThree() {
		int defStrength = unit.getDefensiveStrength();
		assertThat(defStrength, is(3));
	}

	@Test
	public void shouldGetAttackStrengthTwo() {
		int atkStrength = unit.getAttackingStrength();
		assertThat(atkStrength, is(2));
	}
}
