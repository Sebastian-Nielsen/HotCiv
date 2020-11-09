package hotciv.visual;

import hotciv.framework.Game;
import hotciv.stub.StubGame2;
import hotciv.view.tool.UnitMoveTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/** Template code for exercise FRS 36.39 */

public class ShowMove {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Move any unit using the mouse",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Move units to see Game's moveUnit method being called.");

    // TODO: Replace the setting of the tool with your UnitMoveTool implementation.
//    editor.setTool( new SelectionTool(editor) );
    editor.setTool( new UnitMoveTool(editor, game) );
//    editor.setTool( new UnitMoveTool(editor, new GameImpl(new AlphaCivFactory())) );
  }
}
