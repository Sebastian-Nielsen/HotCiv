package hotciv.common;

import hotciv.framework.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Responsibilites:
 * (1) The world, ie. what is at each position:
 *      (1) What tile each position is associated with
 *      (2) What City each position is associated with
 *      (3) What Unit each position is assoicated with
 */
public class World {
    private final Map<Position, City> posToCity = new HashMap<>();
    private final Map<Position, Tile> posToTiles = new HashMap<>();
    private final Map<Position, Unit> posToUnits = new HashMap<>();


    public boolean isUnitAtPos(Position pos) {
        Unit unit = posToUnits.get(pos);
        return unit != null;
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
        return posToTiles.getOrDefault(p, new TileImpl("plains"));
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
            new UnitImpl(unitType, owner)
        );
    }
}
