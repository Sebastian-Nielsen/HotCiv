package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.AttackStrategy;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static hotciv.common.TestHelperMethods.endRound;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEpsilonCiv {
	GameImpl game;
	AttackStrategy attackStrategy;

	private GameImpl gameWithFixedRandomNumber(int[] n) {
		attackStrategy = new CombinedStrengthAttackStrategy(new FixedRandomNumberStrategy(n));
		return new GameImpl(
				new LinearAgingStrategy(),
				new DeterminedWinnerStrategy(),
				new NoSettlerActionStrategy(),
				new NoArcherActionStrategy(),
				new AlphaCivWorldLayoutStrategy(),
				null,
				attackStrategy);
	}


	@Test
	public void blueLegionShouldAttackAndKillRedArcher() {
		int legionRandomNumber = 2;
		int archerRandomNumber = 1;
		game = gameWithFixedRandomNumber(new int[]{legionRandomNumber, archerRandomNumber}); // Create game instance with fixed random number
		Position blueLegionPos = new Position(3, 2);

		// It's red's turn
		game.endOfTurn(); // Now it's blue's turn
		game.moveUnit(blueLegionPos, new Position(2, 1));
		game.endOfTurn(); // Red's turn
		game.endOfTurn(); // Blue's turn
		// Blue legion attacks red's archer
		game.moveUnit(new Position(2, 1), new Position(2, 0));
		// Blue legion should win since (4 * 2) > (3 * 1)
		assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("legion"));
		assertThat(game.getUnitAt(new Position(2, 0)).getOwner(), is(Player.BLUE));
	}


	@Test
	public void redArcherShouldAttackAndBeKilledByBlueLegion() {
		int archerRandomNumber = 1;
		int legionRandomNumber = 1;
		game = gameWithFixedRandomNumber(new int[]{archerRandomNumber, legionRandomNumber}); // Create game instance with fixed random number

		Position redArcher = new Position(2, 0);
		game.moveUnit(redArcher, new Position(2, 1));
		game.endOfTurn(); // Now blue's turn
		game.endOfTurn(); // Now red's turn
		// Red archer attacks blue legion
		game.moveUnit(new Position(2, 1), new Position(3, 2));
		// Red archer should lose since (2 * 1) > (2 * 1) isn't true
		assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
		assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
	}


	@Test
	public void redArcherShouldAttackWithSupportAndKillBlueLegion() {
		int archerRandomNumber = 1;
		int legionRandomNumber = 1;
		game = gameWithFixedRandomNumber(new int[]{archerRandomNumber, legionRandomNumber}); // Create game instance with fixed random number

		Position redSettlerPos = new Position(4, 3);
		game.moveUnit(redSettlerPos, new Position(4, 2));
		endRound(game); // Now its red's turn again

		game.moveUnit(new Position(4, 2), new Position(3, 1));
		endRound(game);

		Position redArcherPos = new Position(2, 0);
		game.moveUnit(redArcherPos, new Position(2, 1));
		endRound(game); // Now its red's turn again


		// Red archer attacks blue legion with support from red settler
		game.moveUnit(new Position(2, 1), new Position(3, 2));
		// Red archer should win since (2+1 * 1) > (2 * 1) isn't true
		assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("archer"));
		assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.RED));
	}

	@Test
	public void redArcherShouldAttackFromHillAndKillBlueLegion() {
		int archerRandomNumber = 1;
		int legionRandomNumber = 2;
		game = gameWithFixedRandomNumber(new int[]{archerRandomNumber, legionRandomNumber}); // Create game instance with fixed random number

		Position hillTilePos = new Position(0, 1);

		Position redArcherPos = new Position(2, 0);
		game.moveUnit(redArcherPos, new Position(1, 1));
		endRound(game);
		game.moveUnit(new Position(1, 1), hillTilePos);
		game.endOfTurn(); // Now its blue's turn


		Position blueLegionPos = new Position(3, 2);
		game.moveUnit(blueLegionPos, new Position(2, 1));
		game.endOfTurn(); // Red spawns a unit in the city at (1, 1)
		game.endOfTurn(); // Now its blue's turn again

		game.moveUnit(new Position(2, 1), new Position(1, 2));
		game.endOfTurn(); // Now its red's turn

		//															   (Atk + ally) * hill * d_1   Def * plain * d_2
		// Red archer attacks blue legion from a hill and wins, since ((2   + 1)    * 2    * 1) > (2   * 1     * 2)
		game.moveUnit(hillTilePos, new Position(1, 2));
		assertThat(game.getUnitAt(new Position(1, 2)).getTypeString(), is("archer"));
		assertThat(game.getUnitAt(new Position(1, 2)).getOwner(), is(Player.RED));
	}


	@Test
	public void blueLegionShouldAttackRedArcherAtHillAndBeKilled() {
		int legionRandomNumber = 1;
		int archerRandomNumber = 1;
		game = gameWithFixedRandomNumber(new int[]{legionRandomNumber, archerRandomNumber}); // Create game instance with fixed random number

		Position hillTilePos = new Position(0, 1);

		Position redArcherPos = new Position(2, 0);
		game.moveUnit(redArcherPos, new Position(1, 1));
		endRound(game);
		game.moveUnit(new Position(1, 1), hillTilePos);
		game.endOfTurn(); // Now its blue's turn


		Position blueLegionPos = new Position(3, 2);
		game.moveUnit(blueLegionPos, new Position(2, 1));
		game.endOfTurn(); // Red spawns a unit in the city at (1, 1)
		game.endOfTurn(); // Now its blue's turn again

		game.moveUnit(new Position(2, 1), new Position(1, 2));
		game.endOfTurn();
		game.endOfTurn(); // Now its blue's turn again

		//																  (Atk + ally) * plain * d_1  (Def + ally) * hill * d_2
		// Blue legion attacks and is killed by red archer at hill, since ((4  + 0)    * 1     * 1) > ((3 + 1)     * 2    * 1)
		game.moveUnit(new Position(1, 2), hillTilePos);
		assertThat(game.getUnitAt(hillTilePos).getTypeString(), is("archer"));
		assertThat(game.getUnitAt(hillTilePos).getOwner(), is(Player.RED));
	}


}
