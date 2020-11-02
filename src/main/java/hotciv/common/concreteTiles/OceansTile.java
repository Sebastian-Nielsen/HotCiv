package hotciv.common.concreteTiles;

import hotciv.common.TileImpl;

import static hotciv.framework.GameConstants.OCEANS;


public class OceansTile extends TileImpl {

	@Override
	public String getTypeString() {
		return OCEANS;
	}

	@Override
	public boolean canUnitTraverse(String unitType) {
		return false;
	}

}
