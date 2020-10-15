package hotciv.variants;

import hotciv.common.CityImpl;
import hotciv.common.GameFactories.ThetaCivFactory;
import hotciv.common.GameFactories.ZetaCivFactory;
import hotciv.common.GameImpl;
import hotciv.common.UnitImpl;
import hotciv.framework.City;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static hotciv.common.TestHelperMethods.*;
import static hotciv.framework.GameConstants.*;
import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ThetaCiv {

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
		posToUnits.put(new Position(10, 1), new UnitImpl(ARCHER, RED));
		posToUnits.put(new Position(9, 1), new UnitImpl(LEGION, BLUE));
		posToUnits.put(new Position(9, 2), new UnitImpl(SETTLER, RED));

		// Init game
		game = new GameImpl(new ThetaCivFactory(layout, posToCities, posToUnits));
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

}
