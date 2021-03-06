package hotciv.variants;

import hotciv.common.*;
import hotciv.common.GameFactories.GammaCivFactory;
import hotciv.framework.Position;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.Player.RED;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestGammaCiv {
	private GameImpl game;
	private Position settlerPos;

	@BeforeEach
	public void SetUp() {
		game = new GameImpl(new GammaCivFactory());
		settlerPos = new Position(4, 3);
	}


	/* TestIntegrated SettlerActionStrategy*/

     /**
     * Test: BuildCitySettlerActionStrategy
     */
    @Test
    public void shouldBuildCityWhenSettlerPerformAction() {
        // Settler performs action
        game.performUnitActionAt(settlerPos);

        // Assert a city is built
        assertNotNull(game.getCityAt(settlerPos)); // to avoid nullpointerException
        MatcherAssert.assertThat(game.getCityAt(settlerPos).getOwner(), is(RED));
    }

    /**
     * Test: BuildCitySettlerActionStrategy
     */
    @Test
    public void settlerShouldBeRemovedAfterPerformingAction() {
        // Settler builds a city and is removed
        game.performUnitActionAt(settlerPos);

        // Assert settler is removed
        assertNull(game.getUnitAt(settlerPos));
    }


	/* TestIntegrated ArcherActionStrategy*/

	/**
	* Test: FortifyArcherActionStrategy
	*/
    @Test
    public void archerShouldDoubleDefStrengthAfterFortify() {
        // Red archers position and the unit
        Position redArcherPos = new Position(2, 0);
        UnitImpl redArcher = (UnitImpl) game.getUnitAt(redArcherPos);
        // The archers defensive stat
        int unFortifiedDef = redArcher.getDefensiveStrength();
        // The archer fortifies
        game.performUnitActionAt(redArcherPos);
        // The new defensive stat should be double the previous defensive stat
        assertThat(redArcher.getDefensiveStrength(), is(unFortifiedDef * 2));
    }

    /**
	* Test: FortifyArcherActionStrategy
	*/
    @Test
    public void archerShouldHalveDefStrengthAfterUnfortifying() {
        // Red archers position and the unit
        Position redArcherPos = new Position(2, 0);
        UnitImpl redArcher = (UnitImpl) game.getUnitAt(redArcherPos);
        // The archers base defensive stat
        int unFortifiedDef = redArcher.getDefensiveStrength();
        game.performUnitActionAt(redArcherPos);
        // The archer unfortifies
        game.performUnitActionAt(redArcherPos);
        // The new defensive stat should be same as the base defensive stat
        assertThat(redArcher.getDefensiveStrength(), is(unFortifiedDef));
    }


}
