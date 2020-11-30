package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import java.util.UUID;

import static hotciv.framework.GameConstants.CARAVAN;
import static hotciv.framework.GameConstants.DESERT;


public class DesertTile extends TileImpl {

	protected final String id;

    public DesertTile() {
        this.id = UUID.randomUUID().toString();
    }
    @Override
	public String getId() {
    	return id;
	}


	@Override
	public String getTypeString() { return DESERT; }

	@Override
	public boolean canUnitTraverse(String unitType) {
		return unitType.equals(CARAVAN);
	}

}
