package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.getPositionFromXY;

public class SetFocusTool extends NullTool {

	private final Game game;

	public SetFocusTool(Game game) {
		this.game = game;
	}

	public void mouseDown(MouseEvent e, int x, int y) {
		Position pos = getPositionFromXY(x, y);
		game.setTileFocus(pos);
	}

}
