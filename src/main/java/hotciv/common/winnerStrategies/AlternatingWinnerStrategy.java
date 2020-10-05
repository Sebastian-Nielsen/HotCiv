package hotciv.common.winnerStrategies;

import hotciv.common.GameImpl;
import hotciv.common.winnerStrategies.CityConquerWinnerStrategy;
import hotciv.common.winnerStrategies.ThreeSuccessfulAttacksWinnerStrategy;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

public class AlternatingWinnerStrategy implements WinnerStrategy {
	private CityConquerWinnerStrategy cityConquerWinnerStrategy = new CityConquerWinnerStrategy();
	private ThreeSuccessfulAttacksWinnerStrategy threeSuccessfulAttacksWinnerStrategy;

	private WinnerStrategy current_state;

	@Override
	public Player determineWinner(GameImpl game) {

		// If 20 rounds haven't passed yet
		if (game.getRoundNumber() < 21)
			// Use cityConquerWinnerStrategy
			current_state = cityConquerWinnerStrategy;
		else
			// Use ThreeSuccessfulAttacksWinnerStrategy
			 current_state = getThreeSuccessfulAttacksWinnerStrategy();

		return current_state.determineWinner(game);
	}

	private WinnerStrategy getThreeSuccessfulAttacksWinnerStrategy() {
		if (threeSuccessfulAttacksWinnerStrategy == null)
			threeSuccessfulAttacksWinnerStrategy = new ThreeSuccessfulAttacksWinnerStrategy();
		return threeSuccessfulAttacksWinnerStrategy;

	}


}
