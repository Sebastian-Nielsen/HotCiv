package hotciv.variants;

import hotciv.common.*;
import hotciv.common.GameFactories.StubEpsilonCivFactory;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.variants.testStubs.StubFixedRandomNumberStrategy;
import org.junit.jupiter.api.Test;


import static hotciv.common.TestHelperMethods.endRound;
import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEpsilonCiv {
	GameImpl game;

	private GameImpl createGameWithFixedRandomNumber(int[] n) {
		return new GameImpl(
				new StubEpsilonCivFactory(new StubFixedRandomNumberStrategy(n))
		);
	}


	@Test
	public void blueShouldWinAfterThreeSuccesfulAttacks() {
		int blueLegionRandomNumber = 6;
		int redSettlerRandomNumber = 1;
		int redArcherRandomNumber = 1;
		int redArcher2RandomNumber = 1;
		int blueArcherRandomNumber = 6;
		// Create game instance with fixed random number
		game = createGameWithFixedRandomNumber(new int[]{blueLegionRandomNumber, redArcherRandomNumber,
										           blueLegionRandomNumber, redSettlerRandomNumber,
										           blueArcherRandomNumber, redArcher2RandomNumber});

		Position blueLegionPos = new Position(3, 2);
		Position redArcherPos = new Position(2, 0);
		Position redSettlerPos = new Position(4, 3);
		Position redCityPos = new Position(1, 1);
		Unit blueLegion = game.getUnitAt(blueLegionPos);


		/* Move blue legion to redArcher */
		game.endOfTurn(); // It's blue's turn now
		game.moveUnit(blueLegionPos, new Position(2, 1));
		game.endOfTurn();
		game.endOfTurn(); // It's blue's turn now
		game.moveUnit(new Position(2, 1), redArcherPos); // Blue Legion attacks redArcher       // successfulAttacksCount = 1
		// blue Legion wins the attack and moves to the red archers position
		//                              legion attack       >    red archer defence
		// Blue legion wins because:   ((4 + 0) * 1) * 6    >     ((3 + 1) * 1) * 1
		assertThat(game.getUnitAt(redArcherPos), is(blueLegion));
		blueLegionPos = redArcherPos;  // Position(2, 0)
		assertNull(game.getUnitAt(redCityPos));
		game.endOfTurn();

		Position redArcher2Pos = redCityPos; // At this point, the red city should spawn a red archer in the city
		assertThat(game.getUnitAt(redArcher2Pos).getTypeString(), is("archer"));

		game.endOfTurn(); // It's blue turn now

		/* Move blue legion to redSettler */
		game.moveUnit(blueLegionPos, new Position(3, 1));
		game.endOfTurn();
		game.endOfTurn(); // It's blue's turn now

		game.moveUnit(new Position(3, 1), new Position(4, 2));
		game.endOfTurn(); // It's red's turn now
		// At this point a blueArcher is spawned north of blue city at pos: (3,1)
		Position blueArcherPos = new Position(3, 1);
		assertThat(game.getUnitAt(blueArcherPos).getTypeString(), is("archer"));
		game.endOfTurn(); // It's blue's turn now

		game.moveUnit(new Position(4,2), redSettlerPos); // Blue Legion attacks redSettler     // successfulAttacksCount = 2
		// blue Legion wins the attack and moves to the red settler position
		//                              legion attack       >    red archer defence
		// Blue legion wins because:   ((4 + 0) * 1) * 6    >     ((3 + 0) * 1) * 1
		game.endOfTurn(); // It's red's turn now

		game.moveUnit(redArcher2Pos, new Position(2, 1));
		redArcher2Pos = new Position(2,1);
		game.endOfTurn(); // It's blue's turn now

		assertNull(game.getWinner());
		game.moveUnit(blueArcherPos, redArcher2Pos); // BlueArcher attacks redArcher2     // successfulAttacksCount = 3
		// blue Legion wins the attack and moves to the red settler position
		//                          blueArcher attack       >     redarcher defence
		// Blue legion wins because:   ((2 + 0) * 1) * 6    >     ((3 + 0) * 1) * 1
		game.endOfTurn();
		assertThat(game.getWinner(), is(BLUE));  // BLUE has 3 successful attacks, so BLUE wins.
	}


	@Test
	public void blueLegionShouldAttackAndKillRedArcher() {
		int legionRandomNumber = 2;
		int archerRandomNumber = 1;
		game = createGameWithFixedRandomNumber(new int[]{legionRandomNumber, archerRandomNumber}); // Create game instance with fixed random number
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
		assertThat(game.getUnitAt(new Position(2, 0)).getOwner(), is(BLUE));
	}


	@Test
	public void redArcherShouldAttackAndBeKilledByBlueLegion() {
		int archerRandomNumber = 1;
		int legionRandomNumber = 1;
		game = createGameWithFixedRandomNumber(new int[]{archerRandomNumber, legionRandomNumber}); // Create game instance with fixed random number

		Position redArcherPos = new Position(2, 0);
		game.moveUnit(redArcherPos, new Position(2, 1));
		game.endOfTurn(); // Now blue's turn
		game.endOfTurn(); // Now red's turn
		// Red archer attacks blue legion
		game.moveUnit(new Position(2, 1), new Position(3, 2));
		// Red archer should lose since (2 * 1) > (2 * 1) isn't true
		assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
		assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(BLUE));
	}


	@Test
	public void redArcherShouldAttackWithSupportAndKillBlueLegion() {
		int archerRandomNumber = 1;
		int legionRandomNumber = 1;
		game = createGameWithFixedRandomNumber(new int[]{archerRandomNumber, legionRandomNumber}); // Create game instance with fixed random number

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
		assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(RED));
	}

	@Test
	public void redArcherShouldAttackFromHillAndKillBlueLegion() {
		int archerRandomNumber = 1;
		int legionRandomNumber = 2;
		game = createGameWithFixedRandomNumber(new int[]{archerRandomNumber, legionRandomNumber}); // Create game instance with fixed random number

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
		assertThat(game.getUnitAt(new Position(1, 2)).getOwner(), is(RED));
	}


	@Test
	public void blueLegionShouldAttackRedArcherAtHillAndBeKilled() {
		int legionRandomNumber = 1;
		int archerRandomNumber = 1;
		game = createGameWithFixedRandomNumber(new int[]{legionRandomNumber, archerRandomNumber}); // Create game instance with fixed random number

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
		assertThat(game.getUnitAt(hillTilePos).getOwner(), is(RED));
	}


}
