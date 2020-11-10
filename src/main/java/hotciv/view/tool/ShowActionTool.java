package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.getPositionFromXY;

public class ShowActionTool extends NullTool {

	private final Game game;

	public ShowActionTool(Game game) {
		this.game = game;
	}

	public void mouseDown(MouseEvent e, int x, int y) {
		Position pos = getPositionFromXY(x, y);

		Unit unit = game.getUnitAt(pos);
		boolean isUnitAtPos = unit != null;

		if (isUnitAtPos && e.isShiftDown()) {
			game.performUnitActionAt(pos);
		}

	}
}
