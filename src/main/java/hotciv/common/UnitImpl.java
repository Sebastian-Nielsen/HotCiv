package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {

    private final String type;
    private final Player owner;

    public UnitImpl(String type, Player owner) {
        this.type = type;
        this.owner = owner;
    }

    @Override
    public String getTypeString() {
        return type;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return 1;
    }

    @Override
    public int getDefensiveStrength() {
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }
}
