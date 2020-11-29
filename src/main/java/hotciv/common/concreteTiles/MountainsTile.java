package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import java.util.UUID;

import static hotciv.framework.GameConstants.MOUNTAINS;


public class MountainsTile extends TileImpl {

    protected final String id;

    public MountainsTile() {
        this.id = UUID.randomUUID().toString();
    }
    @Override
	public String getId() {
    	return id;
	}


	@Override
	public String getTypeString() { return MOUNTAINS; }

	@Override
	public boolean canUnitTraverse(String unitType) {
		return false;
	}

}
