package hotciv.variants;

import hotciv.common.*;
import hotciv.common.GameFactories.ZetaCivFactory;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.common.TestHelperMethods.*;
import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestZetaCiv {

	private GameImpl game;

	@BeforeEach
	public void SetUp() {
		game = new GameImpl(new ZetaCivFactory());

	}

	@Test
	public void redShouldWinWhenRedConquersBlueCityAt4_1Before20RoundsHasPassed() {
		// Red archer moves from (2,0) to (3,1)
		game.moveUnit(new Position(2, 0), new Position(3, 1));
		endRound(game);

		// There should not be a winner now
		assertNull(game.getWinner());

		// the blue city is at
		Position blueCityPos = new Position(4, 1);

		assertNull(game.getWinner());

		// Red archer moves to the blue city tile
		game.moveUnit(new Position(3, 1), blueCityPos);

		assertThat(game.getWinner(), is(RED));
	}

	@Test
	public void redShouldNOTWinWhenRedConquersBlueCityAt4_1After20RoundsHasPassed() {
		// Red archer moves from (2,0) to (3,1)
		game.moveUnit(new Position(2, 0), new Position(3, 1));
		endRound(game);


		// the blue city is at
		Position blueCityPos = new Position(4, 1);

		endRound20Times(game);

		assertNull(game.getWinner());  // there shouldn't be any winner at this point

		// Red archer moves to the blue city tile
		game.moveUnit(new Position(3, 1), blueCityPos);
		assertThat(game.getCityAt(blueCityPos).getOwner(), is(RED));

		assertTrue(game.getRoundNumber() > 20);
		assertNull(game.getWinner());
	}


	/**
	 * This test has been copied from 'TestBetaCiv' with the
	 * difference being that there is no winner when blue
	 * gets three successful attacks, because 20 rounds haven't passed.
	 */
	@Test
	public void blueShouldNOTWinAfterThreeSuccesfulAttacksBeforeRound21() {

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
		assertThat(game.getUnitAt(redArcherPos), Matchers.is(blueLegion));
		blueLegionPos = redArcherPos;  // Position(2, 0)
		assertNull(game.getUnitAt(redCityPos));
		game.endOfTurn();

		Position redArcher2Pos = redCityPos; // At this point, the red city should spawn a red archer in the city
		assertThat(game.getUnitAt(redArcher2Pos).getTypeString(), Matchers.is("archer"));

		game.endOfTurn(); // It's blue turn now

		/* Move blue legion to redSettler */
		game.moveUnit(blueLegionPos, new Position(3, 1));
		game.endOfTurn();
		game.endOfTurn(); // It's blue's turn now

		game.moveUnit(new Position(3, 1), new Position(4, 2));
		game.endOfTurn(); // It's red's turn now
		// At this point a blueArcher is spawned north of blue city at pos: (3,1)
		Position blueArcherPos = new Position(3, 1);
		assertThat(game.getUnitAt(blueArcherPos).getTypeString(), Matchers.is("archer"));
		game.endOfTurn(); // It's blue's turn now

		game.moveUnit(new Position(4,2), redSettlerPos); // Blue Legion attacks redSettler     // successfulAttacksCount = 2
		// blue Legion wins the attack and moves to the red settler position
		game.endOfTurn(); // It's red's turn now

		game.moveUnit(redArcher2Pos, new Position(2, 1));
		redArcher2Pos = new Position(2,1);
		game.endOfTurn(); // It's blue's turn now

		assertNull(game.getWinner());
		game.moveUnit(blueArcherPos, redArcher2Pos); // BlueArcher attacks redArcher2     // successfulAttacksCount = 3
		// blue Legion wins the attack and moves to the red settler position
		assertTrue(game.getRoundNumber() <= 20); // Less than 21 rounds has passed
		assertNull(game.getWinner());
	}

	@Test
	public void redShouldWinAfterThreeSuccesfulAttacksAfterRound21() {

		Position redSettlerPos = new Position(4, 3);
		Position blueCityPos = new Position(4, 1);

		endRound20Times(game);
		// 20 rounds have passed, (we are at round 21), so blue units
		// have spawned all around the blue city.
		assertThat(game.getUnitAt(new Position(3,2)).getOwner(), is(BLUE)); // North-east of city
		assertThat(game.getUnitAt(new Position(4,2)).getOwner(), is(BLUE)); // east of city
		assertThat(game.getUnitAt(new Position(5,2)).getOwner(), is(BLUE)); // south-east of city


		// Move redSettler to the positions of the three blue units and attack them
		game.moveUnit(redSettlerPos, new Position(3,2));
		endRound(game);
		game.moveUnit(new Position(3,2), new Position(4,2));
		endRound(game);

		assertNull(game.getWinner()); // No winner at this point

		game.moveUnit(new Position(4,2), new Position(5,2));

		assertTrue(game.getRoundNumber() > 20);
		assertThat(game.getWinner(), is(RED));  // Red is the winner
	}


	@Test
	public void successfulAttacksBeforeRound21ShouldNotCountTowardsWin() {
		Position redSettlerPos = new Position(4, 3);
		Position blueCityPos = new Position(4, 1);

		endRound10Times(game);
		// 20 rounds have passed, (we are at round 21), so blue units
		// have spawned all around the blue city.
		assertThat(game.getUnitAt(new Position(3,2)).getOwner(), is(BLUE)); // North-east of city
		assertThat(game.getUnitAt(new Position(4,2)).getOwner(), is(BLUE)); // east of city
		assertThat(game.getUnitAt(new Position(5,2)).getOwner(), is(BLUE)); // south-east of city


		// Move redSettler to the positions of the three blue units and attack them
		game.moveUnit(redSettlerPos, new Position(3,2));
		endRound(game);
		game.moveUnit(new Position(3,2), new Position(4,2));
		endRound(game);

		assertNull(game.getWinner()); // No winner at this point

		game.moveUnit(new Position(4,2), new Position(5,2));

		// At this point
		// Blue legion from (3,2) has killed three units in the following order:
		// Red unit at (3,2)
		// Red unit at (4,2)
		// Red unit at (5,2)
		// So succcessful-attacks-count is now: 3


		endRound10Times(game);

		assertNull(game.getWinner());  // Should be no winner yet

		// We have now roundNumber 21, so the successful-attacks-count should be 0
	}


}
