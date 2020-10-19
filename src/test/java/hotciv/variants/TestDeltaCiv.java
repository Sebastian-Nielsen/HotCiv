package hotciv.variants;

import hotciv.common.*;
import hotciv.common.GameFactories.DeltaCivFactory;
import hotciv.framework.City;
import hotciv.framework.Position;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestDeltaCiv {

    GameImpl game;


    /* TestIntegrated WorldLayoutStrategy */

    /**
     * Test: CustomWorldLayoutStrategy
     */
    @Test
    public void shouldGenerateTilesAccordingToSuppliedLayout() {
        // Define how the layout should be generated
        String[] layout =
                new String[]{
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",    // '.' is 'ocean'
                        ".ooMMMoooo..oooo",    // 'o' is 'plains'
                        "...ofooohhoooo..",    // 'M' is 'mountains'
                        ".ofoofooooohhoo.",    // 'f' is 'forest'
                        "...ooo..........",    // 'h' is 'hills'
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
        // Init posToCities
        Map<Position, City> posToCities = new HashMap<>();
        posToCities.put(new Position(8, 12), new CityImpl(RED));
        posToCities.put(new Position(4,  5), new CityImpl(BLUE));

        // Init game
        game = new GameImpl(
                new DeltaCivFactory(layout, posToCities, new HashMap<>())
        );

        // Assert that the world was correctly generated
        assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is("ocean"));
        assertThat(game.getTileAt(new Position(3, 1)).getTypeString(), is("plains"));
        assertThat(game.getTileAt(new Position(3, 3)).getTypeString(), is("mountains"));
        assertThat(game.getTileAt(new Position(9, 1)).getTypeString(), is("forest"));
        assertThat(game.getTileAt(new Position(14, 5)).getTypeString(), is("hills"));
    }
}
