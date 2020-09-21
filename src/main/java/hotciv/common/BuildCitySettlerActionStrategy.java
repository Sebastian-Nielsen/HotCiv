package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Position;

public class BuildCitySettlerActionStrategy implements SettlerActionStrategy {
    @Override
    public void performAction(GameImpl game, Position pos) {
        // Get settler
        UnitImpl settler = (UnitImpl) game.getUnitAt(pos);
        // Create a city at the settler's position
        game.createCityAt(pos, new CityImpl(settler.getOwner()));
        // Remove settler
        game.popUnitAt(pos);
    }

}
