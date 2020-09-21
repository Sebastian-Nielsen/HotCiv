package hotciv.common;

import hotciv.framework.Position;

public interface SettlerActionStrategy {

    public void performAction(GameImpl game, Position pos);
}
