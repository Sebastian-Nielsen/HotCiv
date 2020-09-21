package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.common.TestHelperMethods.endRound;
import static hotciv.common.TestHelperMethods.endRound10Times;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TestIntegratedWinnerStrategy {
    private GameImpl game;

    @Test
    public void shouldMakeRedTheWinnerAt3000BC(){
        game = new GameImpl(new LinearAgingStrategy(), new DeterminedWinnerStrategy(), new BuildCitySettlerActionStrategy());
        endRound10Times(game);
        assertThat(game.getAge(), is(-4000 + 10*100)); // = -3000
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldNotBeAWinnerBefore3000BC(){
        game = new GameImpl(new LinearAgingStrategy(), new DeterminedWinnerStrategy(), new BuildCitySettlerActionStrategy());
        assertThat(game.getWinner(), is(nullValue()));
        game.setAge(-3100);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void redShouldWinWhenRedConquersBlueCityAt4_1() {
        game = new GameImpl(new LinearAgingStrategy(), new CityConquerWinnerStrategy(), new BuildCitySettlerActionStrategy());

        // Red archer moves from (2,0) to (3,1)
        game.moveUnit(new Position(2, 0), new Position(3, 1));
        endRound(game);

        // There should not be a winner now
        assertNull(game.getWinner());

        // the blue city is at
        Position blueCityPos = new Position(4, 1);
        // Red archer moves to the blue city tile
        game.moveUnit(new Position(3, 1), blueCityPos);

        assertThat(game.getWinner(), is(Player.RED));
    }

}