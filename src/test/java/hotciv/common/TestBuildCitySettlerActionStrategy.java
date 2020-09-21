package hotciv.common;

import hotciv.framework.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class TestBuildCitySettlerActionStrategy {
    private GameImpl game;
    private Position settlerPos;

    @BeforeEach
    public void SetUp() {
        game = new GameImpl(new LinearAgingStrategy(), new DeterminedWinnerStrategy());
        settlerPos = new Position(4, 3);
    }

    @Test
    public void shouldBuildCityWhenSettlerPerformAction() {
        // Settler performs action
        game.performUnitActionAt(settlerPos);

        // Assert a city is built
        assertNotNull(game.getCityAt(settlerPos)); // to avoid nullpointerException
        assertThat(game.getCityAt(settlerPos).getOwner(), is(RED));
    }

    @Test
    public void settlerShouldBeRemovedAfterPerformingAction() {
        // Settler builds a city and is removed
        game.performUnitActionAt(settlerPos);

        // Assert settler is removed
        assertNull(game.getUnitAt(settlerPos));
    }
}