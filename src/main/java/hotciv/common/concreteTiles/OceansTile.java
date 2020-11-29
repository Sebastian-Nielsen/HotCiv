package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import java.util.UUID;

import static hotciv.framework.GameConstants.OCEANS;


public class OceansTile extends TileImpl {

    protected final String id;

    public OceansTile() {
        this.id = UUID.randomUUID().toString();
    }
    @Override
	public String getId() {
    	return id;
	}


	@Override
	public String getTypeString() {
		return OCEANS;
	}

	@Override
	public boolean canUnitTraverse(String unitType) {
		return false;
	}

}
