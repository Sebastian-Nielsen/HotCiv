package hotciv.variants;

import hotciv.common.CityImpl;
import hotciv.common.GameFactories.ThetaCivFactory;
import hotciv.common.GameImpl;
import hotciv.common.UnitImpl;
import hotciv.common.concreteUnits.*;
import hotciv.framework.City;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static hotciv.common.TestHelperMethods.*;
import static hotciv.framework.GameConstants.*;
import static hotciv.framework.Player.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TestThetaCiv {

	private GameImpl game;

	@BeforeEach
	public void SetUp() {

		String[] layout = new String[]{
				"...ooModdoo.....",
				"..ohhoooofffoo..",
				".oooooMooo...oo.",
				".ooMMMoooo..oooo",
				"...ofooohhoooo..",
				".ofoofooooohhoo.",
				"...odd..........",
				".ooodo.ooohooM..",
				".ooooo.oohooof..",
				"offfoddo.offoooo",
				"oodddodo...ooooo",
				".ooMMMdooo......",
				"..ooooooffoooo..",
				"....ooooooooo...",
				"..ooohhoo.......",
				".....ooooooooo..",
		};

        // Init posToCities
        Map<Position, City> posToCities = new HashMap<>();
        posToCities.put(new Position(8, 12), new CityImpl(RED));
        posToCities.put(new Position(4,  5), new CityImpl(BLUE));

        // Init posToUnits
		Map<Position, Unit> posToUnits = new HashMap<>();
		posToUnits.put(new Position(10, 1), new ArcherUnit(RED));
		posToUnits.put(new Position(9, 1), new LegionUnit(RED));
		posToUnits.put(new Position(9, 2), new SettlerUnit(RED));
		posToUnits.put(new Position(11, 2), new CaravanUnit(RED));

		// Init game
		game = new GameImpl(new ThetaCivFactory(layout, posToCities, posToUnits));
	}

	@Test
	public void caravanUnitCanTraverseDesertTiles() {
		Position caravanPos = new Position(11, 2);
		Position desertTilePos = new Position(10, 2);
		assertTrue(game.moveUnit(caravanPos, desertTilePos));
	}

	@Test
	public void ordinaryUnitsCannotTraverseDesertTileS() {
		assertThat(game.getTileAt(new Position(10, 2)).getTypeString(), is(DESERT));

		Position archerPos  = new Position(10, 1);
		Position legionPos  = new Position(9, 1);
		Position settlerPos = new Position(9, 2);

		Position desertTilePos = new Position(10, 2);

//		UnitImpl archer  = (UnitImpl) game.getUnitAt(new Position(10, 1));
//		UnitImpl legion  = (UnitImpl) game.getUnitAt(new Position(9, 1));
//		UnitImpl settler = (UnitImpl) game.getUnitAt(new Position(9, 2));

		assertFalse(game.moveUnit(archerPos,  desertTilePos));
		assertFalse(game.moveUnit(legionPos,  desertTilePos));
		assertFalse(game.moveUnit(settlerPos, desertTilePos));
	}

	@Test
	public void caravanInCityShouldIncreasePopulationBy2AndDisappear() {
		Position redCityPos = new Position(8, 12);
		CityImpl redCity = (CityImpl) game.getCityAt(redCityPos);
		redCity.setProduction(CARAVAN); // CARAVAN_COST = 30
		endRound(game); // Red treasury is 6
		endRound(game); // Red treasury is 12
		endRound(game); // Red treasury is 18
		endRound(game); // Red treasury is 24
		endRound(game); // Red treasury is 0 and caravan is spawned in red city
		UnitImpl redCaravan = (UnitImpl) game.getUnitAt(redCityPos);
		assertThat(redCaravan.getTypeString(), is(CARAVAN));
		int preActionSize = redCity.getSize(); // Red city's size before the caravan action
		game.performUnitActionAt(redCityPos); // Caravan performs action and increases city size and disappears
		assertNull(game.getUnitAt(redCityPos)); // The caravan is gone
		assertThat(redCity.getSize(), is(preActionSize + 2)); // Red city's size has increased by 2
	}

	@Test
	public void caravanOutsideCityShouldDoNothing() {
		Position redCityPos = new Position(8, 12);
		CityImpl redCity = (CityImpl) game.getCityAt(redCityPos);
		redCity.setProduction(CARAVAN); // CARAVAN_COST = 30
		endRound(game); // Red treasury is 6
		endRound(game); // Red treasury is 12
		endRound(game); // Red treasury is 18
		endRound(game); // Red treasury is 24
		endRound(game); // Red treasury is 0 and caravan is spawned in red city
		UnitImpl redCaravan = (UnitImpl) game.getUnitAt(redCityPos);
		assertThat(redCaravan.getTypeString(), is(CARAVAN));
		Position caravanNewPos = new Position(8, 11);
		game.moveUnit(redCityPos, caravanNewPos);
		int preActionSize = redCity.getSize(); // Red city's size before the caravan action
		game.performUnitActionAt(caravanNewPos); // caravan performs action, but nothing happens
		assertThat(game.getUnitAt(caravanNewPos).getTypeString(), is(CARAVAN)); // The caravan still exists
		assertThat(redCity.getSize(), is(preActionSize)); // Red city's size is still the default of 1
	}


	@Test
	public void caravanShouldBeAbleToMove2Times() {
		Position redCaravanStartPos = new Position(11, 2); // The starting position of the caravan
		Position redCaravanIntermediatePos = new Position(10, 2); // Position above the caravan
		Position redCaravanFinalPos = new Position(10, 3); // Position to the right of the caravan
		assertTrue(game.moveUnit(redCaravanStartPos, redCaravanIntermediatePos));
		assertTrue(game.moveUnit(redCaravanIntermediatePos, redCaravanFinalPos));
		assertFalse(game.moveUnit(redCaravanFinalPos, redCaravanIntermediatePos));
	}

}
