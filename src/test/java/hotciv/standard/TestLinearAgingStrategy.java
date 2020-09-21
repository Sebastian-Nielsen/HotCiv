package hotciv.standard;

import hotciv.framework.AgingStrategy;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class TestLinearAgingStrategy {

    @Test
    public void shouldIncrementAgeBy100EachRound() {
        AgingStrategy linearAgingStrategy = new LinearAgingStrategy();

        assertThat(linearAgingStrategy.incrementAge(-4000), is(-4000 + 100));
        assertThat(linearAgingStrategy.incrementAge(500), is(500 + 100));
    }

}