package hotciv.view.tool;

import hotciv.framework.Game;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Template for the EndOfTurn Tool exercise (FRS 36.42)...
 *
 * @author Henrik Bærbak Christensen, Aarhus University
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
    // TODO: Remove print statement, and implement end-of-turn behaviour
    System.out.println("TODO: EndOfTurn tool received 'mouse down' event");
  }

}
