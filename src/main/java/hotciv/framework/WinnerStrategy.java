package hotciv.framework;

import hotciv.common.GameImpl;

public interface WinnerStrategy {

    Player determineWinner(GameImpl game);
}
