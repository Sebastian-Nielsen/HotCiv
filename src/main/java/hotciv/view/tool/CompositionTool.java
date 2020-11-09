package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.TURN_SHIELD_TYPE_STRING;
import static hotciv.view.GfxConstants.UNIT_TYPE_STRING;

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
	private HotCivFigure figureBelowClickPoint;

	private Tool state;

	private final ShowActionTool showActionTool;
	private final EndOfTurnTool endOfTurnTool;
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
		nullTool = new NullTool();
	}

	@Override
	public void mouseDown(MouseEvent e, int x, int y) {
		// Find the figure (if any) just below the mouse click position
		figureBelowClickPoint = (HotCivFigure) editor.drawing().findFigure(x, y);

		// Next determine the state of tool to use
		if (figureBelowClickPoint == null) {
			System.out.println("setfocustool is called <--");
			setFocusTool.mouseDown(e, x, y);
			return;
		}

		String typeOfClicked = figureBelowClickPoint.getTypeString();
		System.out.println("The type asdfasdf: " + typeOfClicked);

		if (typeOfClicked.equals(TURN_SHIELD_TYPE_STRING)) {
			state = endOfTurnTool;
		} else if (e.isShiftDown() && typeOfClicked.equals(UNIT_TYPE_STRING)) {
			state = showActionTool;
		} else if (typeOfClicked.equals(UNIT_TYPE_STRING)) {
			setFocusTool.mouseDown(e, x, y);
			state = unitMoveTool;
		} else {
			state = setFocusTool;
		}

//		 Finally, delegate to the selected state
		state.mouseDown(e, x, y);

	}

	@Override
	public void mouseDrag(MouseEvent e, int x, int y) {
		if (state instanceof UnitMoveTool)
			unitMoveTool.mouseDrag(e, x, y);
	}

	@Override
	public void mouseUp(MouseEvent e, int x, int y) {
		if (state instanceof UnitMoveTool)
			unitMoveTool.mouseUp(e, x, y);
	}

}