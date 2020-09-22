package hotciv.framework;

import hotciv.common.GameImpl;

public interface WorldLayoutStrategy {

    /**
     * Generates the tiles and cities of the world.
     * @param game
     */
    public void generateWorld(GameImpl game);
}
