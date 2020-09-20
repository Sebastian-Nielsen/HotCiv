package hotciv.standard;

public class LinearAgingStrategy implements AgingStrategy {
    @Override
    public int incrementAge(int oldAge) {
        return oldAge + 100;
    }
}
