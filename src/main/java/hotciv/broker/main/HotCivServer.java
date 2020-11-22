package hotciv.broker.main;

import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.common.GameFactories.SemiCivFactory;
import hotciv.common.GameImpl;
import hotciv.broker.Invoker.HotCivGameInvoker;
import hotciv.framework.Game;

public class HotCivServer {

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      explainAndDie();
    }
    new HotCivServer(args[0]); // No error handling!
  }

  private static void explainAndDie() {
    System.out.println("Usage: HotCivServer {port}");
    System.out.println("       port = port number for server to listen to");
    System.exit(-1);
  }

  public HotCivServer(String portNo) throws Exception {
    // Define the server side delegates
    Game game = new GameImpl(new SemiCivFactory());
    HotCivGameInvoker gameInvoker = new HotCivGameInvoker(game);

    // Configure the Spark-java servlet
    int port = Integer.parseInt(portNo);
    SocketServerRequestHandler srh;
    srh = new SocketServerRequestHandler(port, gameInvoker);
    srh.start();

    // Welcome
    System.out.println("=== HotCiv Spark based REST Server Request Handler (port:"+port+") ===");
    System.out.println(" Use ctrl-c to terminate!");
  }
}

