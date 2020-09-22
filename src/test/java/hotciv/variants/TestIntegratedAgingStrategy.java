package hotciv.variants;

import hotciv.common.*;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static hotciv.common.TestHelperMethods.*;

public class TestIntegratedAgingStrategy {
    private GameImpl game;

    @Test
    public void shouldIntegrateLinearAgingStrategyCorrectly() {
        game = new GameImpl(new LinearAgingStrategy(), new DeterminedWinnerStrategy(), new BuildCitySettlerActionStrategy(), new NoArcherActionStrategy());
        endRound(game);
        // After ending round age has increased from 4000BC to 3900BC
        assertThat(game.getAge(), is(-4000 + 100));

        endRound40Times(game);

        // After ending round another 40 times, age has increased from 3900BC to 100AD (=-3900 + 40*100)
        assertThat(game.getAge(), is(-3900 + 40*100));
    }

    @Test
    public void shouldIntegrateProgressiveAgingStrategy() {
        game = new GameImpl(new ProgressiveAgingStrategy(), new DeterminedWinnerStrategy(), new BuildCitySettlerActionStrategy(), new NoArcherActionStrategy());

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
