package hotciv.common;

import hotciv.framework.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class TestIntegratedSettlerActionStrategy {
    private GameImpl game;
    private Position settlerPos;

    @BeforeEach
    public void SetUp() {
        settlerPos = new Position(4, 3);
    }


    /**
     * Test: NoSettlerActionStrategy
     */
    @Test
    public void settlerShouldPerformNoAction() {
        game = new GameImpl(new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new NoSettlerActionStrategy(), new NoArcherActionStrategy());

        game.performUnitActionAt(settlerPos);

        // The settler action does nothing:
        // no city is created at settlerPos
        assertNull(game.getCityAt(settlerPos));
        assertThat(game.getUnitAt(settlerPos).getTypeString(), is("settler"));
    }

     /**
     * Test: BuildCitySettlerActionStrategy
     */
    @Test
    public void shouldBuildCityWhenSettlerPerformAction() {
        game = new GameImpl(new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new BuildCitySettlerActionStrategy(), new NoArcherActionStrategy());

        // Settler performs action
        game.performUnitActionAt(settlerPos);

        // Assert a city is built
        assertNotNull(game.getCityAt(settlerPos)); // to avoid nullpointerException
        assertThat(game.getCityAt(settlerPos).getOwner(), is(RED));
    }

    /**
     * Test: BuildCitySettlerActionStrategy
     */
    @Test
    public void settlerShouldBeRemovedAfterPerformingAction() {
        game = new GameImpl(new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new BuildCitySettlerActionStrategy(), new NoArcherActionStrategy());

        // Settler builds a city and is removed
        game.performUnitActionAt(settlerPos);

        // Assert settler is removed
        assertNull(game.getUnitAt(settlerPos));
    }
}

