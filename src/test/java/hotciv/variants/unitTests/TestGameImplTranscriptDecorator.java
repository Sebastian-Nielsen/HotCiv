package hotciv.variants.unitTests;

import hotciv.common.GameFactories.AlphaCivFactory;
import hotciv.common.GameImpl;
import hotciv.framework.Game;
import hotciv.framework.GameImplTranscriptDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestGameImplTranscriptDecorator {
	Game gameTranscripter;
	Game originalGame;
	Game game;

	@BeforeEach
	public void Setup() {
		game = originalGame = new GameImpl(new AlphaCivFactory());
		gameTranscripter = new GameImplTranscriptDecorator(originalGame);
	}

	/**
	 * Toggle whether the gameTranscriptor is on/off
	 */
	public void toggleGameTranscriptor() {
		if (game == originalGame)
			// enable logging
			game = gameTranscripter;
		else
			// Disable logging
			game = originalGame;
	}

	@Test
	public void shouldEnableTranscriptDecorator() {
		toggleGameTranscriptor(); // Turn game Transcriptor on
		game.endOfTurn();
		GameImplTranscriptDecorator g = (GameImplTranscriptDecorator) game;
		assertThat(g.getTranscript(), is("RED ends turn.\n"));
	}

	@Test
	public void shouldDisableTranscriptDecorator() {
		game.endOfTurn();
		toggleGameTranscriptor(); // Turn game Transcriptor on
		GameImplTranscriptDecorator g = (GameImplTranscriptDecorator) game;
		String transcript = g.getTranscript();
		assertNotEquals(transcript, "<>");
	}
}
