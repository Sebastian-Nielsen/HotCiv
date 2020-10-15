package hotciv.variants.unitTests;

import hotciv.common.CityImpl;
import hotciv.common.World;
import hotciv.common.worldLayoutStrategies.CustomWorldLayoutStrategy;
import hotciv.framework.City;
import hotciv.framework.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static hotciv.framework.Player.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TestCustomWorldLayoutStrategy {
    CustomWorldLayoutStrategy customWorldLayoutStrategy;
    private World world;
    private String[] layout;

    private Position redCityPos = new Position(8, 12);
    private Position blueCityPos = new Position(4, 5);

    @BeforeEach
    public void SetUp() {
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
        // Init posToCities
        Map<Position, City> posToCities = new HashMap<>();
        posToCities.put(new Position(8, 12), new CityImpl(RED));
        posToCities.put(new Position(4,  5), new CityImpl(BLUE));

        customWorldLayoutStrategy = new CustomWorldLayoutStrategy(layout, posToCities, new HashMap<>());
        world = new World();
    }

    @Test
    public void shouldGenerateRedCityAt8_12WhenLayoutSupplied() {
        customWorldLayoutStrategy.generateWorld(world);
        assertThat(world.getCityAt(redCityPos).getOwner(), is(RED));
    }

    @Test
    public void shouldGenerateBlueCityAt4_5WhenLayoutSupplied() {
        customWorldLayoutStrategy.generateWorld(world);
        assertThat(world.getCityAt(blueCityPos).getOwner(), is(BLUE));
    }

    @Test
    public void shouldGenerateTilesAccordingToSuppliedLayout() {
        // Generate world using deltaCiv layout strategy
        customWorldLayoutStrategy.generateWorld(world);

        // Assert that the world was correctly generated
        assertThat(world.getTileAt(new Position(0, 0)).getTypeString(), is("ocean"));
        assertThat(world.getTileAt(new Position(3, 1)).getTypeString(), is("plains"));
        assertThat(world.getTileAt(new Position(3, 3)).getTypeString(), is("mountain"));
        assertThat(world.getTileAt(new Position(9, 1)).getTypeString(), is("forest"));
        assertThat(world.getTileAt(new Position(14, 5)).getTypeString(), is("hills"));

    }
}