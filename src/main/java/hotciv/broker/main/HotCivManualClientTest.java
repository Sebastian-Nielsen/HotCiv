package hotciv.broker.main;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.ClientProxy.GameProxy;
import hotciv.framework.Game;
import hotciv.framework.Position;

public class HotCivManualClientTest {

  public static void main(String[] args) throws Exception {
    new HotCivManualClientTest(args[0], args[1]);
  }

  public HotCivManualClientTest(String hostname, String port) {
    System.out.println(
            "=== HotCiv MANUAL TEST Client (Socket) (host: " + hostname + ") ==="
    );

    // Set up Broker part.
    ClientRequestHandler crh = new SocketClientRequestHandler();
    crh.setServer(hostname, Integer.parseInt(port));

    Requestor requestor = new StandardJSONRequestor(crh);

    Game game = new GameProxy(requestor);
    testSimpleMethods(game);
  }

  private void testSimpleMethods(Game game) {
    System.out.println("=== Testing simple methods ===");
    System.out.println(" -> Game age       " + game.getAge());
    System.out.println(" -> Game winner    " + game.getWinner());
    System.out.println(" -> Game PIT       " + game.getPlayerInTurn());
    System.out.println(" -> Game end turn  ");
    game.endOfTurn();
    System.out.println(" -> Now PIT after endOfTurn: " + game.getPlayerInTurn());
    System.out.println(" -> Game end turn  ");
    game.endOfTurn();
    System.out.println(" -> Game end turn  ");
    game.endOfTurn();
    System.out.println(" -> Game end turn  ");
    game.endOfTurn();
    // New unit is spawned at RED city (8,12)
    System.out.println(" -> Game move      " + game.moveUnit(new Position(8,12), new Position(8,13)));


  }

}







//
//public class RestCRUDClient {
//
//  private static final String NANCY_CPR = "251248-1234";
//
//  public static void main(String[] args) {
//    if (args.length < 3) {
//      explainAndDie();
//    }
//
//    System.out.println("RestCRUDClient: Doing the Create Read Update Delete operations.");
//    String host = args[0];
//    int port = Integer.parseInt(args[1]);
//    boolean delete = args[2].equals("true");
//
//    TeleMed teleMed = null;
//    // Create client side delegates
//    teleMed = new GameRESTProxy(host, port);
//
//    // Next go over a set of CRUD operations, optionally skipping the 'delete'
//    TeleObservation teleObs1 = new TeleObservation(NANCY_CPR, 120, 75);
//
//    // POST
//    System.out.println("Create: Perform a POST of tele observation: \n -> "+teleObs1);
//    String id = teleMed.processAndStore(teleObs1);
//    System.out.println(" -> was assigned id: "+id);
//
//    // GET
//    System.out.println("Read: Perform GET of id: "+id);
//    TeleObservation to = teleMed.getObservation(id);
//    System.out.println(" -> Returned: "+to);
//
//    // PUT
////    System.out.println("Update: Perform PUT of id: "+id);
////    to = new TeleObservation(NANCY_CPR, 227.0, 91.0);
////    boolean isValid = teleMed.correct(id, to);
////    System.out.println(" -> outcome: "+isValid);
//
//    // validate that contents has changed.
//    to = teleMed.getObservation(id);
//    System.out.println(" -> New value: "+to);
//
//    if (delete) {
//      // DELETE
//      System.out.println("Delete: Perform DELETE of id: "+id);
//      isValid = teleMed.delete(id);
//      System.out.println(" -> outcome: "+isValid);
//
//      // validate that no observation exists with that id
//      to = teleMed.getObservation(id);
//      System.out.println(" -> New value: "+to);
//    }
//
//    System.out.println("RestCRUDClient - completed.");
//  }
//
//  private static void explainAndDie() {
//    System.out.println("Usage: RestCRUDClient {host} {port} {delete}");
//    System.out.println("       host = name/ip of host");
//    System.out.println("       port = port number of server");
//    System.out.println("       delete = 'true'|'false'; if 'true' the observation will be deleted again");
//    System.exit(-1);
//  }
//}
