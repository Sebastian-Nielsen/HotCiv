package hotciv.common;

import hotciv.framework.Tile;

public abstract class TileImpl implements Tile {

    public abstract String getTypeString();

    public abstract boolean canUnitTraverse(String unitType);

}
