package hotciv.framework;

import hotciv.common.GameImpl;

public interface WinnerStrategy {

    Player getWinner(GameImpl game);
}
