package hotciv.framework;

public interface AgingStrategy {

    /**
     * Increments the age
     * @param oldAge The old age
     * @return The new age
     */
    public int incrementAge(int oldAge);

}
