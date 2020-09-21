package hotciv.common;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

import java.util.Collection;

public class DeterminedWinnerStrategy implements WinnerStrategy {
    @Override
    public Player getWinner(GameImpl game) {
        if (game.getAge() < -3000) return null; else return Player.RED;
    }
}
