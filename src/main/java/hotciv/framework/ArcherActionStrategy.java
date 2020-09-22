package hotciv.framework;

import hotciv.common.GameImpl;

public interface ArcherActionStrategy {
    void performAction(GameImpl game, Position pos);
}
