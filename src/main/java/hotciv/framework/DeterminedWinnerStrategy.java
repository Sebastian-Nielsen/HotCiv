package hotciv.framework;

import hotciv.common.GameImpl;

public class DeterminedWinnerStrategy implements WinnerStrategy {
    @Override
    public Player getWinner(GameImpl game) {
        if (game.getAge() < -3000) return null; else return Player.RED;
    }
}
