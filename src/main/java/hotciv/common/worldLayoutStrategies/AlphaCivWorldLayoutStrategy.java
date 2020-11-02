package hotciv.common.worldLayoutStrategies;

import hotciv.common.CityImpl;
import hotciv.common.World;
import hotciv.common.concreteTiles.*;
import hotciv.common.concreteUnits.*;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.framework.WorldLayoutStrategy;

import static hotciv.framework.GameConstants.*;
import static hotciv.framework.Player.*;

public class AlphaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public void generateWorld(World world) {
        // Initialize tiles
        world.createTileAtPos(new Position(1, 0), new OceansTile());
        world.createTileAtPos(new Position(0, 1), new HillsTile());
        world.createTileAtPos(new Position(2, 2), new MountainsTile());
        // The rest of the tiles are plains

        // Initialize cities
        world.createCityAtPos(new Position(1, 1), new CityImpl(RED));
        world.createCityAtPos(new Position(4, 1), new CityImpl(BLUE));

        // Initialize units
		Unit redArcher = new ArcherUnit(RED);
		Unit blueLegion = new LegionUnit(BLUE);
		Unit redSettler = new SettlerUnit(RED);
        // Initialize units' positions
		world.createUnitAt(new Position(2, 0), redArcher);
		world.createUnitAt(new Position(3, 2), blueLegion);
		world.createUnitAt(new Position(4, 3), redSettler);
    }
}
