package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import static hotciv.framework.GameConstants.MOUNTAINS;


public class MountainsTile extends TileImpl {

	@Override
	public String getTypeString() { return MOUNTAINS; }

	public boolean canUnitTraverse(String unitType) {
		return false;
	}

}
