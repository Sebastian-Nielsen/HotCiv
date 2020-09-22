package hotciv.common;

import hotciv.framework.Position;
import hotciv.framework.WorldLayoutStrategy;

import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;

public class AlphaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public void generateWorld(GameImpl game) {
        // Initialize tiles
        game.createTileAtPos(new Position(1, 0), new TileImpl("ocean"));
        game.createTileAtPos(new Position(0, 1), new TileImpl("hill"));
        game.createTileAtPos(new Position(2, 2), new TileImpl("mountain"));
        // Initialize cities
        game.createCityAtPos(new Position(1, 1), new CityImpl(RED));
        game.createCityAtPos(new Position(4, 1), new CityImpl(BLUE));
    }
}
