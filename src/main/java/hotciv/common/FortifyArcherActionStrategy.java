package hotciv.common;

import hotciv.framework.ArcherActionStrategy;

public class FortifyArcherActionStrategy implements ArcherActionStrategy {
    @Override
    public void performAction(ArcherUnitImpl unit) {
        if (unit.isFortified()) {
            unit.setDefensiveStrength(unit.getDefensiveStrength() / 2);
            unit.setFortified(false);
        } else {
            unit.setDefensiveStrength(unit.getDefensiveStrength() * 2);
            unit.setFortified(true);
        }
    }

}
