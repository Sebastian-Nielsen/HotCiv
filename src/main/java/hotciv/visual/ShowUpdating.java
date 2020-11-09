package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.stub.StubGame2;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Show how GUI changes can be induced by making
    updates in the underlying domain model.

 */
public class ShowUpdating {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click anywhere to see Drawing updates",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.setTool( new UpdateTool(editor, game) );

    editor.showStatus("Click anywhere to state changes reflected on the GUI");
                      
    // Try to set the selection tool instead to see
    // completely free movement of figures, including the icon

    // editor.setTool( new SelectionTool(editor) );
  }
}

/** A tool that simply 'does something' new every time
 * the mouse is clicked anywhere; as a visual testing
 * of the 'from Domain to GUI' data flow is coded correctly*/
class UpdateTool extends NullTool {
  private Game game;
  private DrawingEditor editor;
  public UpdateTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
  }
  private int count = 0;
  public void mouseDown(MouseEvent e, int x, int y) {
    switch(count) {
    case 0: {
      editor.showStatus( "State change: Moving archer to (1,1)" );
      game.moveUnit( new Position(2,0), new Position(1,1) );
      break;
    }
    case 1: {
      editor.showStatus( "State change: Moving archer to (2,2)" );
      game.moveUnit( new Position(1,1), new Position(2,2) );
      break;
    }
    case 2: {
      editor.showStatus( "State change: End of Turn (over to blue)" );
      game.endOfTurn();
      break;
    }
    case 3: {
      editor.showStatus( "State change: End of Turn (over to red)" );
      game.endOfTurn();
      break;
    }
    case 4: {
      editor.showStatus( "State change: Inspect Unit at (4,3)" );
      game.setTileFocus(new Position(4,3));
      break;
    }
    case 5: {
      editor.showStatus( "State change: Inspect City at (1,1)" );
      game.setTileFocus(new Position(1,1));
      break;
    }
    case 6: {
      editor.showStatus( "State change: Perform settler action at (4,3)" );
      game.performUnitActionAt(new Position(4,3));
      break;
    }
      // TODO: Add more state changes for other things to test
    default: {
      editor.showStatus("No more changes in my list...");
    }
    }
    count ++;
  }
}

