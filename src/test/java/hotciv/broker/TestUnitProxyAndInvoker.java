package hotciv.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.ClientProxy.UnitProxy;
import hotciv.broker.Invoker.RootInvoker;
import hotciv.common.UnitImpl;
import hotciv.common.concreteUnits.ArcherUnit;
import hotciv.common.concreteUnits.LegionUnit;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.testStubs.ClientRequestHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.GameConstants.ARCHER;
import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestUnitProxyAndInvoker {

	private Unit archer;
	private Unit legion;

	@BeforeEach
	public void Setup() {
		// Populate nameserver with single unit impl
		NameService ns = new NameServiceImpl();
		UnitImpl archerUnit = new ArcherUnit(RED);
		UnitImpl legionUnit = new LegionUnit(BLUE);
		ns.put(archerUnit.getId(), archerUnit);
		ns.put(legionUnit.getId(), legionUnit);


		// Inject the unitInvoker into our UnitProxy
		Invoker invoker = new RootInvoker(null, ns);

		ClientRequestHandler crh;
		crh = new ClientRequestHandlerStub(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		archer = new UnitProxy(requestor, archerUnit.getId());
		legion = new UnitProxy(requestor, legionUnit.getId());
	}

	@Test
	public void shouldGetRedOwner() {
		Player owner = archer.getOwner();
		assertThat(owner, is(RED));
	}

	@Test
	public void shouldGetBlueOwner() {
		Player owner = legion.getOwner();
		assertThat(owner, is(BLUE));
	}

	@Test
	public void shouldGetArcherTypestring() {
		String owner = archer.getTypeString();
		assertThat(owner, is(ARCHER));
	}

	@Test
	public void shouldGetMoveCountOne() {
		int moveCount = archer.getMoveCount();
		assertThat(moveCount, is(1));
	}

	@Test
	public void shouldGetDefensiveStrengthThree() {
		int defStrength = archer.getDefensiveStrength();
		assertThat(defStrength, is(3));
	}

	@Test
	public void shouldGetAttackStrengthTwo() {
		int atkStrength = archer.getAttackingStrength();
		assertThat(atkStrength, is(2));
	}
}
