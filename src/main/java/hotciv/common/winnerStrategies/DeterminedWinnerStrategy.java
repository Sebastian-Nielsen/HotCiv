package hotciv.common.winnerStrategies;

import hotciv.common.GameImpl;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

public class DeterminedWinnerStrategy implements WinnerStrategy {
    @Override
    public Player determineWinner(GameImpl game) {
        if (game.getAge() < -3000) return null; else return Player.RED;
    }
}
