package hotciv.common;

import hotciv.framework.Player;

public class LegionUnitImpl extends UnitImpl {
    public LegionUnitImpl(Player owner) {
        super("legion", owner);
        defensiveStrength = 2;
    }
}
