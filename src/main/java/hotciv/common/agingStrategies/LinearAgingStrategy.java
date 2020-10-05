package hotciv.common.agingStrategies;

import hotciv.framework.AgingStrategy;

public class LinearAgingStrategy implements AgingStrategy {
    @Override
    public int incrementAge(int oldAge) {
        return oldAge + 100;
    }
}
