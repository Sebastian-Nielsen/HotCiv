package hotciv.framework;

import hotciv.common.TileImpl;
import hotciv.common.World;

public class DeltaCivWorldLayoutStrategy implements WorldLayoutStrategy {
    @Override
    public void generateWorld(World world, String[] layout) {
        
        String line;
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
          line = layout[r];
          for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
            char tileChar = line.charAt(c);

            // Convert tileChar to typeName
            String type = world.convertTileCharToTileTypeName(tileChar);

            Position p = new Position(r,c);
            world.createTileAtPos( p, new TileImpl(type) );
          }
        }
    }



}
