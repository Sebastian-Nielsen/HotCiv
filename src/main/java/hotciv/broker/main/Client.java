package hotciv.broker.main;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.ClientProxy.GameProxy;
import hotciv.framework.Game;
import hotciv.view.tool.CompositionTool;

import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class Client {

	public static void main(String[] args) throws Exception {
		new hotciv.broker.main.Client(args[0], args[1]);
	}

	public Client(String hostname, String port) {
		System.out.println(
				"=== HotCiv Client (Socket) (host: " + hostname + ", port: " + port + ") ==="
		);

		// Set up Broker part.
		ClientRequestHandler crh = new SocketClientRequestHandler();
		crh.setServer(hostname, Integer.parseInt(port));
		Requestor requestor = new StandardJSONRequestor(crh);
		Game game = new GameProxy(requestor);


		// === GUI
		DrawingEditor editor =
				new MiniDrawApplication( "Shift-Click unit to invoke its action",
						new HotCivFactory4(game) );


		editor.open();
		editor.showStatus("THIS IS THE REAL CLIENT DEAL");

		editor.setTool( new CompositionTool(editor, game) );
	}




//
//  private void testSimpleMethods(Game game) {
//    System.out.println("=== Testing simple methods ===");
//    System.out.println(" -> Game age       " + game.getAge());
//    System.out.println(" -> Game winner    " + game.getWinner());
//    System.out.println(" -> Game PIT       " + game.getPlayerInTurn());
//    System.out.println(" -> Game end turn  ");
//    game.endOfTurn();
//    System.out.println(" -> Now PIT after endOfTurn: " + game.getPlayerInTurn());
//    System.out.println(" -> Game end turn  ");
//    game.endOfTurn();
//    System.out.println(" -> Game end turn  ");
//    game.endOfTurn();
//    System.out.println(" -> Game end turn  ");
//    game.endOfTurn();
//    // New unit is spawned at RED city (8,12)
//    System.out.println(" -> Game move      " + game.moveUnit(new Position(8,12), new Position(8,13)));
//
//
//  }

}
