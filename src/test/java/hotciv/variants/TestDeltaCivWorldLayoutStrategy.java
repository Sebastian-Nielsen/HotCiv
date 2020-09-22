package hotciv.variants;

import hotciv.common.World;
import hotciv.framework.DeltaCivWorldLayoutStrategy;
import hotciv.framework.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hotciv.framework.Player.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class TestDeltaCivWorldLayoutStrategy {
    DeltaCivWorldLayoutStrategy deltaCivWorldLayoutStrategy;
    private World world;
    private String[] layout;

    private Position redCityPos = new Position(8, 12);
    private Position blueCityPos = new Position(4, 5);

    @BeforeEach
    public void SetUp() {
        deltaCivWorldLayoutStrategy = new DeltaCivWorldLayoutStrategy();
        world = new World();

        // Define how the layout should be generated
        layout = new String[]{
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
    }

    @Test
    public void shouldGenerateRedCityAt8_12WhenLayoutSupplied() {
        deltaCivWorldLayoutStrategy.generateWorld(world, layout);
        assertThat(world.getCityAt(redCityPos).getOwner(), is(RED));
    }

    @Test
    public void shouldGenerateBlueCityAt4_5WhenLayoutSupplied() {
        deltaCivWorldLayoutStrategy.generateWorld(world, layout);
        assertThat(world.getCityAt(blueCityPos).getOwner(), is(BLUE));
    }

    @Test
    public void shouldGenerateTilesAccordingToSuppliedLayout() {
        // Generate world using deltaCiv layout strategy
        deltaCivWorldLayoutStrategy.generateWorld(world,layout);

        // Assert that the world was correctly generated
        assertThat(world.getTileAt(new Position(0, 0)).getTypeString(), is("ocean"));
        assertThat(world.getTileAt(new Position(3, 1)).getTypeString(), is("plains"));
        assertThat(world.getTileAt(new Position(3, 3)).getTypeString(), is("mountain"));
        assertThat(world.getTileAt(new Position(9, 1)).getTypeString(), is("forest"));
        assertThat(world.getTileAt(new Position(14, 5)).getTypeString(), is("hills"));

    }
}