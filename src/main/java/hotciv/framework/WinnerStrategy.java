package hotciv.framework;

import hotciv.common.GameImpl;

import java.util.Collection;

public interface WinnerStrategy {

    Player getWinner(GameImpl game);
}
