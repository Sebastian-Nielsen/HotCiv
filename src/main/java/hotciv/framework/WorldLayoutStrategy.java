package hotciv.framework;

import hotciv.common.World;

public interface WorldLayoutStrategy {

    /**
     * Generates the tiles and cities of the world.
     * @param world The world in which to generate the layout
     *
     */
    public void generateWorld(World world);
}
