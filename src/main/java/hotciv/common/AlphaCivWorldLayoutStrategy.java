package hotciv.common;

import hotciv.framework.Position;
import hotciv.framework.WorldLayoutStrategy;

import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;

public class AlphaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public void generateWorld(World world) {
        // Initialize tiles
        world.createTileAtPos(new Position(1, 0), new TileImpl("ocean"));
        world.createTileAtPos(new Position(0, 1), new TileImpl("hill"));
        world.createTileAtPos(new Position(2, 2), new TileImpl("mountain"));
        // Initialize cities
        world.createCityAtPos(new Position(1, 1), new CityImpl(RED));
        world.createCityAtPos(new Position(4, 1), new CityImpl(BLUE));
    }
}
