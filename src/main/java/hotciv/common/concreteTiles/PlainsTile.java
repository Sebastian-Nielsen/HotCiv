package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import static hotciv.framework.GameConstants.PLAINS;


public class PlainsTile extends TileImpl {

	public String getTypeString() { return PLAINS; }

	@Override
	public boolean canUnitTraverse(String unitType) {
		return true;
	}

}
