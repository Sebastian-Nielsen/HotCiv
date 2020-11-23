package hotciv.visual;

import hotciv.framework.Game;
import hotciv.stub.StubGame2;
import hotciv.view.tool.EndOfTurnTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/** Template code for exercise FRS 36.42. */

public class ShowEndOfTurn {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click top shield to end the turn",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click to shield to see Game's endOfTurn method being called.");

    editor.setTool( new EndOfTurnTool(editor, game) );
  }
}
