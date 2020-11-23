package hotciv.testStubs;

import hotciv.framework.RandomNumberStrategy;

public class StubFixedRandomNumberStrategy implements RandomNumberStrategy {
	private final int[] fixedNumbers;
	private int currentIndex = 0;

	public StubFixedRandomNumberStrategy(int[] fixedNumbers) {
		this.fixedNumbers = fixedNumbers;
	}

	/**
	 * @return The next number in the array of fixed numbers
	 */
	public int getRandomSixSidedDieNumber() {
		currentIndex++;
		return fixedNumbers[currentIndex - 1];
	}
}
