package hotciv.view.tool;

import hotciv.common.GameImpl;
import hotciv.framework.Game;
import hotciv.view.figure.TextFigure;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/**
 * Template for the CompositionTool exercise (FRS 36.44).
 * Composition tool is basically a State Pattern, similar
 * to MiniDraw's SelectionTool. That is, upon mouse-down
 * it must determine what the user wants (from analyzing
 * what graphical elements have been clicked - unit?
 * city? tile? turn-shield? etc.) and then set its
 * internal tool state to the appropriate tool - and
 * then delegate the mouse down request to that tool.
 */
public class CompositionTool extends NullTool {
	private final DrawingEditor editor;
	private final Game game;
	private Figure figureBelowClickPoint;
	private Tool state;


	private final ShowActionTool showActionTool;
	private final EndOfTurnTool endOfTurnTool;
	private final RefreshTool refreshTool;
	private final SetFocusTool setFocusTool;
	private final UnitMoveTool unitMoveTool;
	private final NullTool nullTool;

	public CompositionTool(DrawingEditor editor, Game game) {
		state = new NullTool();
		this.editor = editor;
		this.game = game;
		state = null;

		showActionTool = new ShowActionTool(game);
		endOfTurnTool = new EndOfTurnTool(editor, game);
		setFocusTool = new SetFocusTool(game);
		unitMoveTool = new UnitMoveTool(editor, game);
		refreshTool = new RefreshTool(editor, game);
		nullTool = new NullTool();
	}

	@Override
	public void mouseDown(MouseEvent e, int x, int y) {
		// Find the figure (if any) just below the mouse click position
		figureBelowClickPoint = editor.drawing().findFigure(x, y);

		// Don't do anything if we click a textFigure
		if (figureBelowClickPoint == null || figureBelowClickPoint instanceof TextFigure) {
			setFocusTool.mouseDown(e, x, y);
			return;
		}

		setFocusTool.mouseDown(e, x, y);
		endOfTurnTool.mouseDown(e, x, y);
		refreshTool.mouseDown(e, x, y);
		showActionTool.mouseDown(e, x, y);
		unitMoveTool.mouseDown(e, x, y);
	}

	@Override
	public void mouseDrag(MouseEvent e, int x, int y) {
		//if (state instanceof UnitMoveTool)
		unitMoveTool.mouseDrag(e, x, y);
	}

	@Override
	public void mouseUp(MouseEvent e, int x, int y) {
		//if (state instanceof UnitMoveTool)
		unitMoveTool.mouseUp(e, x, y);
	}

}