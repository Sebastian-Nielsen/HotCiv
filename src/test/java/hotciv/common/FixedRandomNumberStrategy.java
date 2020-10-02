package hotciv.common;

import hotciv.framework.RandomNumberStrategy;

public class FixedRandomNumberStrategy implements RandomNumberStrategy {
	private final int[] fixedNumbers;
	private int currentIndex = 0;

	public FixedRandomNumberStrategy(int[] fixedNumbers) {
		this.fixedNumbers = fixedNumbers;
	}

	/**
	 * @return The next number in the array of fixed numbers
	 */
	public int getRandomNumber() {
		currentIndex++;
		return fixedNumbers[currentIndex - 1];
	}
}
