package hotciv.variants;

import hotciv.common.GameImpl;
import hotciv.common.LinearAgingStrategy;
import hotciv.framework.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.common.TestHelperMethods.endRound10Times;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TestIntegratedWinnerStrategy {
    private GameImpl game;

    @BeforeEach
    public void SetUp(){
        game = new GameImpl(new LinearAgingStrategy());
    }

    @Test
    public void shouldMakeRedTheWinnerAt3000BC(){
        endRound10Times(game);
        assertThat(game.getAge(), is(-4000 + 10*100)); // = -3000
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldNotBeAWinnerBefore3000BC(){
        assertThat(game.getWinner(), is(nullValue()));
        game.setAge(-3100);
        assertThat(game.getWinner(), is(nullValue()));
    }

}