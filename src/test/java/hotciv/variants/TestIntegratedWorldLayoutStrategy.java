package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.DeltaCivWorldLayoutStrategy;
import hotciv.framework.Position;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestIntegratedWorldLayoutStrategy {

    GameImpl game;

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

        game = new GameImpl(
                new LinearAgingStrategy(),
                new DeterminedWinnerStrategy(),
                new NoSettlerActionStrategy(),
                new DeltaCivWorldLayoutStrategy(),
                layout
        );

        // Assert that the world was correctly generated
        assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is("ocean"));
        assertThat(game.getTileAt(new Position(3, 1)).getTypeString(), is("plains"));
        assertThat(game.getTileAt(new Position(3, 3)).getTypeString(), is("mountain"));
        assertThat(game.getTileAt(new Position(9, 1)).getTypeString(), is("forest"));
        assertThat(game.getTileAt(new Position(14, 5)).getTypeString(), is("hills"));
    }
}
