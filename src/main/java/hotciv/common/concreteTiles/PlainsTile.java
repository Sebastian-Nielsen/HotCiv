package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import java.util.UUID;

import static hotciv.framework.GameConstants.PLAINS;


public class PlainsTile extends TileImpl {

	protected final String id;

    public PlainsTile() {
        this.id = UUID.randomUUID().toString();
    }
    @Override
	public String getId() {
    	return id;
	}


	@Override
	public String getTypeString() { return PLAINS; }

	@Override
	public boolean canUnitTraverse(String unitType) {
		return true;
	}

}
