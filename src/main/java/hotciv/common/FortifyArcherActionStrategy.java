package hotciv.common;

import hotciv.framework.ArcherActionStrategy;

public class FortifyArcherActionStrategy implements ArcherActionStrategy {

	/**
	 * Precondition: unit should be of type archer
	 * @param unit Unit to perform the action of
	 */
	@Override
	public void performAction(UnitImpl unit) {
		if (unit.isFortified()) {
			unit.setDefensiveStrength(unit.getDefensiveStrength() / 2);
			unit.setFortified(false);
		} else {
			unit.setDefensiveStrength(unit.getDefensiveStrength() * 2);
			unit.setFortified(true);
		}
	}

}
