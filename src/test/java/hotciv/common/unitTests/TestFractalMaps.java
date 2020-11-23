package hotciv.common.unitTests;

import hotciv.common.FractalMapGenerator;
import hotciv.common.GameFactories.DeltaCivFactory;
import hotciv.common.GameImpl;
import hotciv.framework.Game;
import hotciv.framework.Position;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestFractalMaps {

	private Game game;

	@Test
	public void shouldGenerateFractalMaps() {
		Set<String> set = new HashSet<>();

		for (int i=0; i<25; i++) {
			// generate a new fractal layout
			String[] layout = FractalMapGenerator.makeFractalLandscape();

			// Init game
			game = new GameImpl(
					new DeltaCivFactory(layout, new HashMap<>(), new HashMap<>())
			);

			// Add the tile at pos (0,0) to the set
			String tileType = game.getTileAt(new Position(0,0)).getTypeString();
			set.add(tileType);
		}

		assertNotEquals(set.size(), 1);
	}

}
