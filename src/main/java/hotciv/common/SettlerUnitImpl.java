package hotciv.common;

import hotciv.framework.Player;

public class SettlerUnitImpl extends UnitImpl {
    public SettlerUnitImpl(Player owner) {
        super("settler", owner);
        defensiveStrength = 3;
    }
}
