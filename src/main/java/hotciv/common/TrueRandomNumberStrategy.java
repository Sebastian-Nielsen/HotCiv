package hotciv.common;

import java.util.Random;
import hotciv.framework.RandomNumberStrategy;

public class TrueRandomNumberStrategy implements RandomNumberStrategy {
	private Random rand = new Random();

	@Override
	public int getRandomSixSidedDieNumber() {
		// Return a random in interval [1; 6]
		return rand.nextInt(6) + 1;
	}
}
