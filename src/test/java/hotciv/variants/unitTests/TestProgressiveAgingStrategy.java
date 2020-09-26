package hotciv.variants.unitTests;

import hotciv.common.ProgressiveAgingStrategy;
import hotciv.framework.AgingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestProgressiveAgingStrategy {
    private AgingStrategy agingStrategy;

    @BeforeEach
    public void SetUp() {
        agingStrategy = new ProgressiveAgingStrategy();
    }

    @Test
    public void ageShouldIncrementWith100Between4000BCand100BC() {
        assertThat(agingStrategy.incrementAge(-4000), is(-4000 + 100));
        assertThat(agingStrategy.incrementAge(-200), is(-200 + 100));
    }

    @Test
    public void ageShouldIncrementTo1BCFrom100BC() {
        assertThat(agingStrategy.incrementAge(-100), is(-1));
    }

    @Test
    public void ageShouldIncrementTo1ADFrom1BC() {
        assertThat(agingStrategy.incrementAge(-1), is(1));
    }

    @Test
    public void ageShouldIncrementTo50ADFrom1AD() {
        assertThat(agingStrategy.incrementAge(1), is(50));
    }

    @Test
    public void ageShouldIncrementWith50Between50ADand1750AD() {
        assertThat(agingStrategy.incrementAge(50), is(50+50));
        assertThat(agingStrategy.incrementAge(1700), is(1700+50));
    }

    @Test
    public void ageShouldIncrementWith25Between1750ADand1900AD() {
        assertThat(agingStrategy.incrementAge(1750), is(1750+25));
        assertThat(agingStrategy.incrementAge(1875), is(1875+25));
    }

    @Test
    public void ageShouldIncrementWith5Between1900ADand1970AD() {
        assertThat(agingStrategy.incrementAge(1900), is(1900+5));
        assertThat(agingStrategy.incrementAge(1965), is(1965+5));
    }

    @Test
    public void ageShouldIncrementWith1After1970AD() {
        assertThat(agingStrategy.incrementAge(1970), is(1970+1));
        assertThat(agingStrategy.incrementAge(2000), is(2000+1));
    }
}