package hotciv.common.settlerActionStrategies;

import hotciv.common.CityImpl;
import hotciv.common.GameImpl;
import hotciv.common.UnitImpl;
import hotciv.framework.Position;
import hotciv.framework.SettlerActionStrategy;

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
