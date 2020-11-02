package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import static hotciv.framework.GameConstants.FOREST;


public class ForestTile extends TileImpl {

	@Override
	public String getTypeString() { return FOREST; }

	@Override
	public boolean canUnitTraverse(String unitType) { return true; }

}
