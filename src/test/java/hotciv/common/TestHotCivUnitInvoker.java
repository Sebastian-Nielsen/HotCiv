package hotciv.common;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.ClientProxy.UnitProxy;
import hotciv.broker.Invoker.HotCivUnitInvoker;
import hotciv.broker.NameServer;
import hotciv.broker.NameServerImpl;
import hotciv.common.concreteUnits.ArcherUnit;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.testStubs.ClientRequestHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.GameConstants.ARCHER;
import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestHotCivUnitInvoker {

	private Unit unit;

	@BeforeEach
	public void Setup() {
		// Populate nameserver with single unit impl
		NameServer ns = new NameServerImpl();
		UnitImpl archer = new ArcherUnit(RED);
		ns.put(archer.getId(), archer);


		// Inject the unitInvoker into our UnitProxy
		Invoker invoker = new HotCivUnitInvoker();

		ClientRequestHandler crh;
		crh = new ClientRequestHandlerStub(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		unit = new UnitProxy(requestor, archer.getId());
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
