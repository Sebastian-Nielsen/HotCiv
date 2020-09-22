package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


public class TestDeterminedWinnerStrategy {

    @Test
    public void redShouldWinAt3000BC() {
        WinnerStrategy determinedWinner = new DeterminedWinnerStrategy();
        GameImpl game = new GameImpl(
                new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new BuildCitySettlerActionStrategy(),
                new AlphaCivWorldLayoutStrategy(),
                null
        );
        game.setAge(-3100);
        assertNull(determinedWinner.getWinner(game));
        game.setAge(-3000);
        assertThat(determinedWinner.getWinner(game), is(Player.RED));
    }


}