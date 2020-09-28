package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.jupiter.api.Test;

import static hotciv.common.TestHelperMethods.*;
import static hotciv.common.TestHelperMethods.endRound5Times;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestBetaCiv {
    private GameImpl game;


    /* TestIntegrated WinnerStrategy */
    @Test
    public void redShouldWinWhenRedConquersBlueCityAt4_1() {
        game = new GameImpl(
                new LinearAgingStrategy(),
                new CityConquerWinnerStrategy(),
                new BuildCitySettlerActionStrategy(),
                new NoArcherActionStrategy(),
                new AlphaCivWorldLayoutStrategy(),
                null
        );

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

	/* TestIntegrated AgingStrategy */

    @Test
    public void shouldIntegrateProgressiveAgingStrategy() {
        game = new GameImpl(
                new ProgressiveAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new BuildCitySettlerActionStrategy(),
                new NoArcherActionStrategy(),
                new AlphaCivWorldLayoutStrategy(),
                null
        );

        endRound(game); // age increases from 4000BC to 3900BC

        assertThat(game.getAge(), is(-4000 + 100));

        endRound20Times(game);
        endRound10Times(game);
        endRound5Times(game);
        endRound(game);
        endRound(game);
        endRound(game);

        // 200BC to 100BC (=-3900 + 3800)
        assertThat(game.getAge(), is(-3900 + (20 + 5 + 5 + 5 + 3)*100));

        endRound(game);

        // 100BC to 1BC
        assertThat(game.getAge(), is(-1));

        endRound(game);

        // 1BC to 1AD
        assertThat(game.getAge(), is(1));
    }


}
