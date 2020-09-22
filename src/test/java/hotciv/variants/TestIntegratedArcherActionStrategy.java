package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TestIntegratedArcherActionStrategy {
    private GameImpl game;


    @Test
    public void archerShouldPerformNoAction() {
        game = new GameImpl(new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new NoSettlerActionStrategy(),
                new NoArcherActionStrategy());

        // Red archers position and the unit
        Position redArcherPos = new Position(2, 0);
        UnitImpl redArcher = (UnitImpl) game.getUnitAt(redArcherPos);
        // The archers defensive stat
        int unFortifiedDef = redArcher.getDefensiveStrength();
        // The archer fortifies
        game.performUnitActionAt(redArcherPos);
        // The new defensive stat should be the same after action is performed
        assertThat(redArcher.getDefensiveStrength(), is(unFortifiedDef));
    }

    @Test
    public void archerShouldDoubleDefStrengthAfterFortify() {
        game = new GameImpl(new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new NoSettlerActionStrategy(),
                new FortifyArcherActionStrategy());

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

    @Test
    public void archerShouldHalveDefStrengthAfterUnfortifying() {
        game = new GameImpl(new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new NoSettlerActionStrategy(),
                new FortifyArcherActionStrategy());

        // Red archers position and the unit
        Position redArcherPos = new Position(2, 0);
        UnitImpl redArcher = (UnitImpl) game.getUnitAt(redArcherPos);
        // The archers base defensive stat
        int unFortifiedDef = redArcher.getDefensiveStrength();
        // The archer fortifies
        game.performUnitActionAt(redArcherPos);
        // The archer unfortifies
        game.performUnitActionAt(redArcherPos);
        // The new defensive stat should be same as the base defensive stat
        assertThat(redArcher.getDefensiveStrength(), is(unFortifiedDef));
    }

}