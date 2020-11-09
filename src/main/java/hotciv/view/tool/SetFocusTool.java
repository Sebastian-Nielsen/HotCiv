package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.getPositionFromXY;

public class SetFocusTool extends NullTool {

	private final DrawingEditor editor;
	private final Game game;

	public SetFocusTool(DrawingEditor editor, Game game) {
		this.editor = editor;
		this.game = game;
	}

	public void mouseDown(MouseEvent e, int x, int y) {
		Drawing model = editor.drawing();

		Figure figure = model.findFigure(x, y);
		Position pos = getPositionFromXY(x, y);

		game.setTileFocus(pos);


	}

}
