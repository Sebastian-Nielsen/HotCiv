package hotciv.common;

import hotciv.framework.Tile;

import java.util.UUID;

public abstract class TileImpl implements Tile {

    private final String id;

    public TileImpl() {
        this.id = UUID.randomUUID().toString();
    }

    public abstract String getTypeString();

    public abstract boolean canUnitTraverse(String unitType);

    public String getId() {
        return id;
    }
}
