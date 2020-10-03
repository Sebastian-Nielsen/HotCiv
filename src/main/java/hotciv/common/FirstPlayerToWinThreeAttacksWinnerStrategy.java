package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

public class FirstPlayerToWinThreeAttacksWinnerStrategy implements WinnerStrategy {

	private int successfulAttacksToWin = 3;


	/**
	 * @param game
	 * @return The player that has won; if no player has won: null.
	 */
	@Override
	public Player getWinner(GameImpl game) {

		// For each player
		for (Player player : Player.values()) {

			// If the player has more than three successful attacks
			if (game.getSuccessfulAttacksCountOf(player) >= successfulAttacksToWin)
				// The player has won
				return player;

		}
		// No player has more than three successful attacks
		return null;
	}


}
