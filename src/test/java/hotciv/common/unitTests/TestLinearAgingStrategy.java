package hotciv.common.unitTests;

import hotciv.common.agingStrategies.LinearAgingStrategy;
import hotciv.framework.AgingStrategy;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestLinearAgingStrategy {

    @Test
    public void shouldIncrementAgeBy100EachRound() {
        AgingStrategy linearAgingStrategy = new LinearAgingStrategy();

        assertThat(linearAgingStrategy.incrementAge(-4000), is(-4000 + 100));
        assertThat(linearAgingStrategy.incrementAge(500), is(500 + 100));
    }

}