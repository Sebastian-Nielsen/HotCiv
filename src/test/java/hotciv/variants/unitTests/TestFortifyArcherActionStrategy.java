package hotciv.variants.unitTests;

import hotciv.common.archerActionStrategies.FortifyArcherActionStrategy;
import hotciv.common.UnitImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.Player.RED;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class TestFortifyArcherActionStrategy {

    private FortifyArcherActionStrategy strategy;
    private UnitImpl archerUnit;

    @BeforeEach
    void setUp() {
        strategy =  new FortifyArcherActionStrategy();
        archerUnit = new UnitImpl("archer", RED);
    }


    @Test
    public void shouldFortify(){
        // Fortify the archer
        strategy.performAction(archerUnit);
        // The archer should be fortified
        assertTrue(archerUnit.isFortified());
    }

    @Test
    public void shouldUnfortify(){
        // Fortify the archer
        strategy.performAction(archerUnit);
        // Unfortify the archer
        strategy.performAction(archerUnit);
        // Archer should be unfortified
        assertFalse(archerUnit.isFortified());
    }

    @Test
    public void shouldDoubleDefStrength(){
        int baseDef = archerUnit.getDefensiveStrength();
        // Fortify the archer
        strategy.performAction(archerUnit);
        // The archers defensive strength should double
        assertThat(archerUnit.getDefensiveStrength(), is(baseDef * 2));
    }

    @Test
    public void shouldHalveDefStrength(){
        int baseDef = archerUnit.getDefensiveStrength();
        // Fortify the archer
        strategy.performAction(archerUnit);
        // Unfortify the archer
        strategy.performAction(archerUnit);
        // The archers defensive strength should be the same as the base
        assertThat(archerUnit.getDefensiveStrength(), is(baseDef));
    }
}