package hotciv.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.ClientProxy.CityProxy;
import hotciv.broker.Invoker.RootInvoker;
import hotciv.common.CityImpl;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.testStubs.ClientRequestHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.GameConstants.ARCHER;
import static hotciv.framework.GameConstants.foodFocus;
import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestCityProxyAndInvoker {

	private City redCity;
	private City blueCity;


	@BeforeEach
	public void Setup() {
		// Populate nameService
		NameService ns = new NameServiceImpl();
		CityImpl redCityImpl = new CityImpl(RED);
		CityImpl blueCityImpl = new CityImpl(BLUE);
		ns.put(redCityImpl.getId(), redCityImpl);
		ns.put(blueCityImpl.getId(), blueCityImpl);

		// Inject CityInvoker into CityProxy
		Invoker invoker = new RootInvoker(null, ns);

		ClientRequestHandler crh;
		crh = new ClientRequestHandlerStub(invoker);

		Requestor requestor = new StandardJSONRequestor(crh);

		redCity = new CityProxy(requestor, redCityImpl.getId());
		blueCity = new CityProxy(requestor, blueCityImpl.getId());

	}

	@Test
	public void shouldGetRedAsCityOwner() {
		Player owner = redCity.getOwner();
		assertThat(owner, is(RED));
	}

	@Test
	public void shouldGetBlueAsCityOwner() {
		Player owner = blueCity.getOwner();
		assertThat(owner, is(BLUE));
	}

	@Test
	public void shouldGetSizeOne() {
		int size = redCity.getSize();
		assertThat(size, is(1));
	}

	@Test
	public void shouldGetZeroTreasury() {
		int treasury = redCity.getTreasury();
		assertThat(treasury, is(0));
	}

	@Test
	public void shouldGetArcherProduction() {
		String production = redCity.getProduction();
		assertThat(production, is(ARCHER));
	}

	@Test
	public void shouldGetFoodFocusAsWorkForceFocus() {
		String workForceFocus = redCity.getWorkforceFocus();
		assertThat(workForceFocus, is(foodFocus));
	}

}
