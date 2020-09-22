package hotciv.common;

import hotciv.framework.Position;
import hotciv.framework.SettlerActionStrategy;

public class NoSettlerActionStrategy implements SettlerActionStrategy {
    private GameImpl game;

    @Override
    public void performAction(GameImpl game, Position pos) {
    }
}
