package hotciv.standard;

import hotciv.framework.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static hotciv.standard.TestHelperMethods.*;

public class TestIntegrated {
    private GameImpl game;


    @Test
    public void shouldIncrementYearBy100EachRound() {
        game = new GameImpl(new LinearAgingStrategy());
        endRound(game);
        assertThat(game.getAge(), is(-4000 + 100));
    }

    @Test
    public void shouldIntegrateProgressiveAgingStrategy() {
        game = new GameImpl(new ProgressiveAgingStrategy());

        endRound(game); // age increases from 4000BC to 3900BC

        assertThat(game.getAge(), is(-3900));
    }

}
