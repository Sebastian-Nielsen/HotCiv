package hotciv.common;

import hotciv.framework.Player;

public class ArcherUnitImpl extends UnitImpl {
    private boolean isFortified;

    public ArcherUnitImpl(Player owner) {
        super("archer", owner);
        defensiveStrength = 3;
    }

    public void setFortified(boolean fortified) {
        isFortified = fortified;
    }

    public boolean isFortified() {
        return isFortified;
    }
}
