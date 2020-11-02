package hotciv.common.concreteUnits;

import hotciv.common.CityImpl;
import hotciv.common.GameImpl;
import hotciv.common.UnitImpl;
import hotciv.framework.Player;
import hotciv.framework.Position;

import static hotciv.framework.GameConstants.*;

public class CaravanUnit extends UnitImpl {

	public CaravanUnit(Player owner) {
		super(owner);

		this.defensiveStrength = CARAVAN_DEFENSIVE_STRENGTH;
		this.attackingStrength = CARAVAN_ATTACK_STRENGTH;
		this.moveCount = CARAVAN_TRAVEL_DISTANCE;

		movesLeft = CARAVAN_TRAVEL_DISTANCE;
	}

	public void performAction(GameImpl game, Position pos) {
		CityImpl city = (CityImpl) game.getCityAt(pos);
		boolean isCaravanInCity = city != null;
		if (isCaravanInCity) {
			city.setSize(city.getSize() + CARAVAN_SIZE_ACTION_INCREASE);
			game.popUnitAt(pos);
		}
	}

	@Override
	public String getTypeString() {
		return CARAVAN;
	}

}
