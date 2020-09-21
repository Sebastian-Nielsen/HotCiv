package hotciv.common;

public class TestHelperMethods {

   /**
   * End the round: end the turn of all players
   * precondition: It should be red's turn
   */
  public static void endRound(GameImpl game) {
    game.endOfTurn(); // End red's turn
    game.endOfTurn(); // End blue's turn
  }

  public static void endRound5Times(GameImpl game) {
      endRound(game);
      endRound(game);
      endRound(game);
      endRound(game);
      endRound(game);
  }

  public static void endRound20Times(GameImpl game) {
      endRound5Times(game);
      endRound5Times(game);
      endRound5Times(game);
      endRound5Times(game);
  }

  public static void endRound40Times(GameImpl game) {
      endRound20Times(game);
      endRound20Times(game);
  }

}
