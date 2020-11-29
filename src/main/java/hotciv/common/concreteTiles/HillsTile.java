package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import java.util.UUID;

import static hotciv.framework.GameConstants.HILLS;


public class HillsTile extends TileImpl {

	protected final String id;

    public HillsTile() {
        this.id = UUID.randomUUID().toString();
    }
	@Override
	public String getId() { return id; }


	@Override
	public String getTypeString() { return HILLS; }

	@Override
	public boolean canUnitTraverse(String unitType) { return true; }

}
