package hotciv.view.tool;

import hotciv.common.GameImpl;
import hotciv.framework.Game;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.*;
import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.REFRESH_BUTTON_X;
import static hotciv.view.GfxConstants.REFRESH_BUTTON_Y;


public class RefreshTool extends NullTool {
	private final DrawingEditor editor;
	private final Game game;

	public RefreshTool(DrawingEditor editor, Game game) {
		this.editor = editor;
		this.game = game;
	}

	@Override
	public void mouseDown(MouseEvent e, int x, int y) {
		super.mouseDown(e, x, y);

		Figure figure = editor.drawing().findFigure(x, y);

		if (figure == null) return;

		Point point = figure.displayBox().getLocation();

		if (point.getX() == REFRESH_BUTTON_X &&
				point.getY() == REFRESH_BUTTON_Y){
			//game.requestUpdate();
			editor.drawing().requestUpdate();
		}

	}

}