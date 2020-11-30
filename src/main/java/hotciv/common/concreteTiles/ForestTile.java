package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import java.util.UUID;

import static hotciv.framework.GameConstants.FOREST;


public class ForestTile extends TileImpl {

    protected final String id;

    public ForestTile() {
        this.id = UUID.randomUUID().toString();
    }
    @Override
	public String getId() {
    	return id;
	}


	@Override
	public String getTypeString() { return FOREST; }

	@Override
	public boolean canUnitTraverse(String unitType) { return true; }

}
