package hotciv.common.winnerStrategies;

import hotciv.common.GameImpl;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

import java.util.HashMap;
import java.util.Map;

public class ThreeSuccessfulAttacksWinnerStrategy implements WinnerStrategy {

	private final int successfulAttacksToWin = 3;
	private Map<Player, Integer> successfulAttackSoFarOfPlayer = initPlayerToSuccessfulAttacksSoFar();

	/**
	 * Update the 'successful attack count so far of player-in-turn' by
	 * adding the former 'successful attack count SO FAR of player-in-turn'
	 * to the 'successful attack count THIS TURN of player-in-turn'.
	 * @param game
	 */
	private void updateSuccessfulAttackSoFarOfPlayerInTurn(GameImpl game) {
		Player playerInTurn = game.getPlayerInTurn();

		int successfulAttacksThisTurn = game.getSuccessfulAttacksThisTurn();
		int formerSuccessfulAttackSoFarOfPlayer = getSuccessfulAttackSoFarOfPlayer(playerInTurn);

		int newNumberOfSuccessfulAttackSoFarOfPlayer =
				formerSuccessfulAttackSoFarOfPlayer + successfulAttacksThisTurn;

		setSuccessfulAttacksOf(playerInTurn, newNumberOfSuccessfulAttackSoFarOfPlayer);
	}


	@Override
	public Player determineWinner(GameImpl game) {

		// Add the successful attack this turn to the cumulative successful att.
		updateSuccessfulAttackSoFarOfPlayerInTurn(game);

		// For each player
		for (Player player : Player.values()) {

			// If the player has more than three successful attacks (starts counting from instance init)
			boolean has3OrMoreSuccessfulAttacks = successfulAttackSoFarOfPlayer.get(player) >= successfulAttacksToWin;

			if (has3OrMoreSuccessfulAttacks)
				// The player has won
				return player;

		}

		// No player has more than three successful attacks
		return null;
	}

		private Map<Player, Integer> initPlayerToSuccessfulAttacksSoFar() {
		Map<Player, Integer> playerToSuccessfulAttacksSoFar = new HashMap<>();
		for (Player player : Player.values())
			playerToSuccessfulAttacksSoFar.put(player, 0);
		return playerToSuccessfulAttacksSoFar;
	}

	private void setSuccessfulAttacksOf(Player playerInTurn, int value) {
		successfulAttackSoFarOfPlayer.put(playerInTurn, value);
	}

	private int getSuccessfulAttackSoFarOfPlayer(Player player) {
		return successfulAttackSoFarOfPlayer.get(player);
	}

}
