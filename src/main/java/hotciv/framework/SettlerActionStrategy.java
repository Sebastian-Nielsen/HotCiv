package hotciv.framework;

import hotciv.common.GameImpl;
import hotciv.framework.Position;

public interface SettlerActionStrategy {

    public void performAction(GameImpl game, Position pos);
}
