package hotciv.common;

import hotciv.framework.AgingStrategy;

public class ProgressiveAgingStrategy implements AgingStrategy {
    @Override
    public int incrementAge(int oldAge) {
        if (-4000 <= oldAge && oldAge < -100) {
            return oldAge + 100;
        } else if (oldAge == -100) {
            return -1;
        } else if (oldAge == -1) {
            return 1;
        } else if (oldAge == 1) {
            return 50;
        } else if (50 <= oldAge && oldAge < 1750) {
            return oldAge + 50;
        } else if (1750 <= oldAge && oldAge < 1900) {
            return oldAge + 25;
        } else if (1900 <= oldAge && oldAge < 1970) {
            return oldAge + 5;
        } else if (1970 <= oldAge) {
            return oldAge + 1;
        } else {
            throw new RuntimeException("Age increment is not defined for this time period");
        }
    }
}
