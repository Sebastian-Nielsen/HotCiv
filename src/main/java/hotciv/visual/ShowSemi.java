package hotciv.visual;

import hotciv.common.GameFactories.SemiCivFactory;
import hotciv.common.GameImpl;
import hotciv.view.tool.CompositionTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;


public class ShowSemi {

  public static void main(String[] args) {
    GameImpl game = new GameImpl(new SemiCivFactory()); // StubGame2();

    DrawingEditor editor =
      new MiniDrawApplication( "SemiCiv", new HotCivFactory4(game));
    editor.open();
    editor.showStatus("Shift-Click on unit to see Game's performAction method being called.");

    // TODONE: Replace the setting of the tool with your ActionTool implementation.
    editor.setTool( new CompositionTool(editor, game) );
  }
}
