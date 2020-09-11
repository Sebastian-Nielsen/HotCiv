package hotciv.standard;

import hotciv.framework.*;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;

/** Skeleton class for AlphaCiv test cases
*/
public class TestAlphaCiv {
  private Game game;
  private City redCity;
  private City blueCity;

  /** Fixture for alphaciv testing. */
  @BeforeEach
  public void setUp() {
    game = new GameImpl();
    redCity = game.getCityAt(new Position(1, 1));
    blueCity = game.getCityAt(new Position(4, 1));
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
     assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void shouldAlwaysBeRedThenBlueThenYellowThenGreenThenRed() {
    // It's red's turn
    game.endOfTurn(); // Red ends his turn
    // It's blue's turn
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));

    game.endOfTurn(); // Blue ends his turn
    // It's yellow's turn
    assertThat(game.getPlayerInTurn(), is(Player.YELLOW));

    game.endOfTurn(); // Yellow ends his turn
    // It's Green's turn
    assertThat(game.getPlayerInTurn(), is(Player.GREEN));

    game.endOfTurn(); // Green ends his turn
    // It's Red's turn
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void cityShouldProduce6ProductionAfterEachRound() {
    int productionBefore = redCity.getTreasury();
    assertThat(productionBefore, is(0));
    endRound();
    int productionAfter = redCity.getTreasury();
    assertThat(productionAfter, is(6));
    assertThat(productionAfter - productionBefore, is(6));
  }

  @Test
  public void cityPopulationIsAlwaysOne() {
    assertThat(redCity.getSize(), is(1));
    endRound();
    assertThat(redCity.getSize(), is(1));
  }

  /**
   * End the round: end the turn of all players
   * precondition: It should be red's turn
   */
  private void endRound() {
    game.endOfTurn(); // End red's turn
    game.endOfTurn(); // End blue's turn
    game.endOfTurn(); // End yellow's turn
    game.endOfTurn(); // End green's turn
  }

  @Test
  public void redsCityShouldBeAtPos1_1() {
    assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
  }

  @Test
  public void bluesCityShouldBeAtPos4_1() {
    assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldStartGameAt4000BC(){
    assertThat(game.getAge(), is(-4000));
  }


  @Test
  public void shouldIncrementYearBy100EachRound(){
    game.endOfTurn(); // Red ends his turn
    game.endOfTurn(); // Blue ends his turn
    game.endOfTurn(); // Yellow ends his turn
    game.endOfTurn(); // Green ends his turn
    assertThat(game.getAge(), is(-4000 + 100));
  }


  @Test
  public void redShouldWinGameAt3000BC(){
    game.setAge(-3000);
    assertThat(game.getWinner(), is(Player.RED));
  }

  @Test
  public void shouldNotBeAWinnerBefore3000BC(){
    assertThat(game.getWinner(), is(nullValue()));
    game.setAge(-3100);
    assertThat(game.getWinner(), is(nullValue()));
  }


}
