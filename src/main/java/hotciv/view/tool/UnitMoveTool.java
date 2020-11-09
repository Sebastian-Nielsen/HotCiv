package hotciv.view.tool;


import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.*;

public class UnitMoveTool extends NullTool {
	private final Game game;
	private final DrawingEditor editor;

	/**
	 * the figure that is being dragged. If null then its operation is not that of
	 * dragging a figure (or a set of figures)
	 */
	private Figure draggedFigure;
	private Figure figure;
	private int fLastX;
	private int fLastY;
	private Position unitFromPos;


	public UnitMoveTool(DrawingEditor editor, Game game) {
		this.editor = editor;
		this.game = game;
	}


	@Override
	public void mouseDown(MouseEvent e, int x, int y) {
		Drawing model = editor.drawing();

		model.lock();

		figure = model.findFigure(x, y);

		if (figure == null) { return; }

		unitFromPos = getPositionFromXY(x, y);
		Unit unit = game.getUnitAt(unitFromPos);

		if (unit == null) { return; }

		boolean isAllyUnit = unit.getOwner() == game.getPlayerInTurn();
		if (isAllyUnit) {
			draggedFigure = figure;
			fLastX = x;
			fLastY = y;
		}
	}

	@Override
	public void mouseDrag(MouseEvent e, int x, int y) {
		if (draggedFigure == null) { return; }
		draggedFigure.moveBy(x - fLastX, y - fLastY);
		fLastX = x;
		fLastY = y;
	}

	@Override
	public void mouseUp(MouseEvent e, int x, int y) {
		editor.drawing().unlock();


		Position unitToPos = getPositionFromXY(x, y);
		boolean moveSuccess = game.moveUnit(unitFromPos, unitToPos);

		if (moveSuccess)
			draggedFigure.moveBy(getXFromColumn(unitToPos.getColumn()), getYFromRow(unitToPos.getRow()));
		else
			draggedFigure.moveBy(getXFromColumn(unitFromPos.getColumn()), getYFromRow(unitFromPos.getRow()));

		draggedFigure = null;
	}

}