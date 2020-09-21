package hotciv.standard;

public class TestHelperMethods {

   /**
   * End the round: end the turn of all players
   * precondition: It should be red's turn
   */
  public static void endRound(GameImpl game) {
    game.endOfTurn(); // End red's turn
    game.endOfTurn(); // End blue's turn
  }
}
