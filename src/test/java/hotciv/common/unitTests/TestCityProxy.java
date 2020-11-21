package hotciv.common.unitTests;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.common.distribution.CityProxy;
import hotciv.common.distribution.HotCivCityInvoker;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.testStubs.ClientRequestHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.GameConstants.ARCHER;
import static hotciv.framework.GameConstants.foodFocus;
import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestCityProxy {

	private City city;

	@BeforeEach
	public void Setup() {
		Invoker invoker = new HotCivCityInvoker();

		ClientRequestHandler crh;
		crh = new ClientRequestHandlerStub(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		city = new CityProxy(requestor);
	}

	@Test
	public void shouldGetRedAsCityOwner() {
		Player owner = city.getOwner();
		assertThat(owner, is(RED));
	}

	@Test
	public void shouldGetSizeOne() {
		int size = city.getSize();
		assertThat(size, is(1));
	}

	@Test
	public void shouldGetZeroTreasury() {
		int treasury = city.getTreasury();
		assertThat(treasury, is(0));
	}

	@Test
	public void shouldGetArcherProduction() {
		String production = city.getProduction();
		assertThat(production, is(ARCHER));
	}

	@Test
	public void shouldGetFoodFocusAsWorkForceFocus() {
		String workForceFocus = city.getWorkforceFocus();
		assertThat(workForceFocus, is(foodFocus));
	}

}
