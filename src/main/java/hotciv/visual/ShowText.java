package hotciv.visual;

import hotciv.framework.Game;
import hotciv.stub.StubGame1;
import hotciv.view.GfxConstants;
import hotciv.view.figure.TextFigure;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

import java.awt.*;
import java.awt.event.MouseEvent;

/** Test the TextFigure to display age in
 * the status panel.

 */
public class ShowText {
  
  public static void main(String[] args) {

    Game game = new StubGame1();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click to see age text change...",  
                               new HotCivFactory3(game) );
    editor.open();
    TextFigure tf = new TextFigure("4000 BC",
                                   new Point(GfxConstants.AGE_TEXT_X,
                                             GfxConstants.AGE_TEXT_Y) );
    editor.drawing().add(tf);
    editor.setTool( new ChangeAgeTool(tf) );
  }
}

// A test stub ChangeAgeTool. Use it as template/idea for your
// real implementation that reads the age from Game.
class ChangeAgeTool extends NullTool {
  private TextFigure textFigure;
  public ChangeAgeTool(TextFigure tf) {
    textFigure = tf;
  }
  int count = 0;
  public void mouseDown(MouseEvent e, int x, int y) {
    count++;
    textFigure.setText( ""+(4000-count*100)+" BC" );
  }
}
