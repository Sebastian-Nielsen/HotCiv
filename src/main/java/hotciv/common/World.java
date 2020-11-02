package hotciv.common;

import hotciv.common.concreteTiles.*;
import hotciv.common.concreteUnits.*;
import hotciv.framework.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static hotciv.framework.GameConstants.*;

/**
 * Responsibilites:
 * (1) The world, ie. what is at each position:
 *      (1) What tile each position is associated with
 *      (2) What City each position is associated with
 *      (3) What Unit each position is assoicated with
 *          (1)
 */
public class World {
    private final Map<Position, City> posToCity = new HashMap<>();
    private final Map<Position, Tile> posToTiles = new HashMap<>();
    private final Map<Position, Unit> posToUnits = new HashMap<>();


    public boolean isUnitAtPos(Position pos) {
        Unit unit = posToUnits.get(pos);
        return unit != null;
    }


    /**
     * Return the tile object the tile char represents
     * E.g. tile char 'o' corresponds to a 'plains' object
     * @return Tile object of the given tile char
     */
    public TileImpl convertTileCharToTile(char tileChar) {
        if ( tileChar == '.' ) return new OceansTile();
        if ( tileChar == 'o' ) return new PlainsTile();
        if ( tileChar == 'M' ) return new MountainsTile();
        if ( tileChar == 'f' ) return new ForestTile();
        if ( tileChar == 'h' ) return new HillsTile();
        if ( tileChar == 'd' ) return new DesertTile();
        else throw new RuntimeException("Failed to convert tileChar");
    }


    /**
     * Return the unit object the tile type-string represents
     * E.g. tile unit 'archer' corresponds to a 'ArcherUnit' object
     * @owner The owner of the unit object
     * @return Unit object of the given type-string
     */
    public UnitImpl getUnitObjectFromTypeString(String unitType, Player owner) {
        if (unitType.equals(ARCHER)) return new ArcherUnit(owner);
        if (unitType.equals(SETTLER)) return new SettlerUnit(owner);
        if (unitType.equals(LEGION)) return new LegionUnit(owner);
        if (unitType.equals(CARAVAN)) return new CaravanUnit(owner);
        else throw new RuntimeException("No unit with type-string: " + unitType);
    }

    /* SETTER METHODS */
    public void createCityAt(Position pos, CityImpl city) {
        posToCity.put(pos, city);
    }

    public void createTileAtPos(Position pos, TileImpl tile) {
        posToTiles.put(pos, tile);
    }

    public void createCityAtPos(Position pos, CityImpl city) {
        posToCity.put(pos, city);
    }

    public void createUnitAt(Position pos, Unit unit) {
        posToUnits.put(pos, unit);
    }

    /* GETTER METHODS */
    public Collection<Unit> getAllUnits() {
        return posToUnits.values();
    }

    public Collection<City> getAllCities() {
        return posToCity.values();
    }

    public City getCityAt(Position pos) {
        return posToCity.getOrDefault(pos, null);
    }

    public Set<Map.Entry<Position, City>> getSetOfPosCityEntry() {
        return posToCity.entrySet();
    }

    public Tile getTileAt(Position p) {
        return posToTiles.getOrDefault(p, new PlainsTile());
    }


    public Unit getUnitAt(Position p) {
        return posToUnits.getOrDefault(p, null);
    }

    public Unit popUnitAt(Position pos) {
        return posToUnits.remove(pos);
    }

    public void spawnUnitAtPos(Position pos, String unitType, Player owner) {
        posToUnits.put(
            pos,
            getUnitObjectFromTypeString(unitType, owner)
        );
    }
}
