package hotciv.variants;

import hotciv.common.GameImpl;
import hotciv.framework.Game;
import hotciv.framework.Position;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TestIntegratedSettlerActionStrategy {
    private GameImpl game;

    @Test
    public void settlerShouldPerformNoAction() {
        Position settlerPos = new Position(4, 3);

        game.performUnitActionAt(settlerPos);

        // The settler action does nothing:
        // no city is created at settlerPos
        assertNull(game.getCityAt(settlerPos));
        assertThat(game.getUnitAt(settlerPos).getTypeString(), is("settler"));
    }
}
