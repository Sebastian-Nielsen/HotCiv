package hotciv.view.tool;

import hotciv.framework.Game;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.*;
import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.TURN_SHIELD_X;
import static hotciv.view.GfxConstants.TURN_SHIELD_Y;

/** Template for the EndOfTurn Tool exercise (FRS 36.42)...
 *
 * A specialized tool to end the turn (the endOfTurn) only
 * when the top shield in the age section is clicked.
 */
public class EndOfTurnTool extends NullTool {
	private final DrawingEditor editor;
	private final Game game;

	public EndOfTurnTool(DrawingEditor editor, Game game) {
		this.editor = editor;
		this.game = game;
	}

	@Override
	public void mouseDown(MouseEvent e, int x, int y) {
		super.mouseDown(e, x, y);
		// TODONE: Remove print statement, and implement end-of-turn behaviour

		Figure figure = editor.drawing().findFigure(x, y);

		if (figure == null) return;

		Point point = figure.displayBox().getLocation();

		if (point.getX() == TURN_SHIELD_X &&
				point.getY() == TURN_SHIELD_Y){
			game.endOfTurn();
		}

	}

}
