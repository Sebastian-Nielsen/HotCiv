package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinnerStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.common.TestHelperMethods.endRound;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


public class TestCityConquerWinnerStrategy {
    private GameImpl game;

    @BeforeEach
    void setUp() {
        game = new GameImpl(
                new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new BuildCitySettlerActionStrategy(),
                new NoArcherActionStrategy(),
                new AlphaCivWorldLayoutStrategy(),
                null);
    }

    @Test
    public void redShouldWinWhenRedHasConqueredAllCities(){
        game.moveUnit(new Position(2, 0), new Position(3, 1));
        endRound(game);
        game.moveUnit(new Position(3, 1), new Position(4, 1));
        endRound(game);
        WinnerStrategy cityConquerWinner = new CityConquerWinnerStrategy();
        assertThat(cityConquerWinner.getWinner(game), is(Player.RED));
    }

}