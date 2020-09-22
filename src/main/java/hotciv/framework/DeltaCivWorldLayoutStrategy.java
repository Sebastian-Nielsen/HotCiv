package hotciv.framework;

import hotciv.common.CityImpl;
import hotciv.common.TileImpl;
import hotciv.common.World;

import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;

public class DeltaCivWorldLayoutStrategy implements WorldLayoutStrategy {

    /**
     * Precondition: Layout is not null
     * @param world The world in which to generate the layout
     * @param layout An optional custom layout
     */
    @Override
    public void generateWorld(World world, String[] layout) {
        generateCities(world);
        generateTiles(world, layout);
    }

    private void generateCities(World world) {
        world.createCityAtPos(new Position(8, 12), new CityImpl(RED));
        world.createCityAtPos(new Position(4,  5), new CityImpl(BLUE));
    }

    private void generateTiles(World world, String[] layout) {
        String line;

        // For each row
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            line = layout[r];

            // For each column
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {

                // Get tileChar at the given column
                char tileChar = line.charAt(c);

                // Convert tileChar to typeName
                String type = world.convertTileCharToTileTypeName(tileChar);

                // Create tile at the given row and column of the given type
                world.createTileAtPos( new Position(r, c), new TileImpl(type));
            }
        }
    }


}
