package hotciv.common;

import hotciv.common.concreteTiles.*;
import hotciv.common.concreteUnits.ArcherUnit;
import hotciv.common.concreteUnits.CaravanUnit;
import hotciv.common.concreteUnits.LegionUnit;
import hotciv.common.concreteUnits.SettlerUnit;
import hotciv.framework.Player;
import hotciv.framework.Tile;

import static hotciv.framework.GameConstants.*;

/**
 * Responsibilities:
 * (1) Convert from Type to another
 */

public class Converter {


	/**
	 * Return the tile object the tile char represents
	 * E.g. tile char 'o' corresponds to a 'plains' object
	 *
	 * @return Tile object of the given tile char
	 */
	public static TileImpl convertTileCharToTile(char tileChar) {
		if (tileChar == '.') return new OceansTile();
		if (tileChar == 'o') return new PlainsTile();
		if (tileChar == 'M') return new MountainsTile();
		if (tileChar == 'f') return new ForestTile();
		if (tileChar == 'h') return new HillsTile();
		if (tileChar == 'd') return new DesertTile();
		else throw new RuntimeException("Failed to convert tileChar");
	}

	/**
	 * Return the unit object the tile type-string represents
	 * E.g. tile unit 'archer' corresponds to a 'ArcherUnit' object
	 *
	 * @return Unit object of the given type-string
	 * @owner The owner of the unit object
	 */
	public static UnitImpl convertTypeStringToUnitObject(String unitType, Player owner) {
		if (unitType.equals(ARCHER)) return new ArcherUnit(owner);
		if (unitType.equals(SETTLER)) return new SettlerUnit(owner);
		if (unitType.equals(LEGION)) return new LegionUnit(owner);
		if (unitType.equals(CARAVAN)) return new CaravanUnit(owner);
		else throw new RuntimeException("No unit with type-string: " + unitType);
	}

	public static Tile convertTypestringToTileObject(String typestring) {
		if (DESERT.equals(typestring)) return new DesertTile();
		if (FOREST.equals(typestring)) return new ForestTile();
		if (HILLS.equals(typestring)) return new HillsTile();
		if (MOUNTAINS.equals(typestring)) return new MountainsTile();
		if (OCEANS.equals(typestring)) return new OceansTile();
		if (PLAINS.equals(typestring)) return new PlainsTile();
		else throw new RuntimeException("No Tile matched with type-string: " + typestring);
	}


}
