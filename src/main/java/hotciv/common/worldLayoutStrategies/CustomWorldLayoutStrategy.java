package hotciv.common.worldLayoutStrategies;

import hotciv.common.CityImpl;
import hotciv.common.TileImpl;
import hotciv.common.UnitImpl;
import hotciv.common.World;
import hotciv.framework.*;

import java.util.Map;
import java.util.Set;

import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;

public class CustomWorldLayoutStrategy implements WorldLayoutStrategy {

    private Map<Position, Unit> posToUnits;
    private Map<Position, City> posToCities;
    private String[] layout;

    public CustomWorldLayoutStrategy(String[] layout, Map<Position, City> posToCities, Map<Position, Unit> posToUnits) {
        this.layout = layout;
        this.posToCities = posToCities;
        this.posToUnits = posToUnits;
    }

    /**
     * @param world The world in which to generate the layout
     */
    @Override
    public void generateWorld(World world) {
        generateCities(world);
        spawnUnits(world);
        generateTiles(world, layout);
    }

    private void spawnUnits(World world) {
        for (Map.Entry<Position, Unit> e : posToUnits.entrySet()) {
            Position pos  =            e.getKey();
            UnitImpl unit = (UnitImpl) e.getValue();

            world.createUnitAt(pos, unit);
        }
    }

    public void setLayout(String[] newLayout) {
        layout = newLayout;
    }

    private void generateCities(World world) {
        for (Map.Entry<Position, City> e : posToCities.entrySet()) {
            Position pos  =            e.getKey();
            CityImpl city = (CityImpl) e.getValue();

            world.createCityAtPos(pos, city);
        }
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
