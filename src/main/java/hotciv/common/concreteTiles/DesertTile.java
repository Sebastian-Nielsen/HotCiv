package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import static hotciv.framework.GameConstants.CARAVAN;
import static hotciv.framework.GameConstants.DESERT;


public class DesertTile extends TileImpl {

	public String getTypeString() { return DESERT; }

	@Override
	public boolean canUnitTraverse(String unitType) {
		return unitType.equals(CARAVAN);
	}

}
