package hotciv.framework;

public class DeterminedWinnerStrategy implements WinnerStrategy {
    @Override
    public Player getWinner(int age) {
        if (age < -3000) return null; else return Player.RED;
    }
}
