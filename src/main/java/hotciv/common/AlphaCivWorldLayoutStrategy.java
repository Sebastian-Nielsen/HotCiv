package hotciv.common;

import hotciv.framework.Position;
import hotciv.framework.WorldLayoutStrategy;

import static hotciv.framework.GameConstants.*;
import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;

public class AlphaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public void generateWorld(World world, String[] layout) {
        // Initialize tiles
        world.createTileAtPos(new Position(1, 0), new TileImpl(OCEANS));
        world.createTileAtPos(new Position(0, 1), new TileImpl(HILLS));
        world.createTileAtPos(new Position(2, 2), new TileImpl(MOUNTAINS));
        // The rest of the tiles are plains

        // Initialize cities
        world.createCityAtPos(new Position(1, 1), new CityImpl(RED));
        world.createCityAtPos(new Position(4, 1), new CityImpl(BLUE));
    }
}
