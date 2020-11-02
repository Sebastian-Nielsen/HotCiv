package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import static hotciv.framework.GameConstants.HILLS;


public class HillsTile extends TileImpl {

	@Override
	public String getTypeString() { return HILLS; }

	@Override
	public boolean canUnitTraverse(String unitType) { return true; }

}
