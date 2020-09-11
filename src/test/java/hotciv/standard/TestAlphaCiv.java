package hotciv.standard;

import hotciv.framework.*;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
  public void oceanTileAtPos1_0() {
    assertThat(game.getTileAt(new Position(1,0)).getTypeString(), is("ocean"));
  }

  @Test
  public void hillTileAtPos0_1() {
    assertThat(game.getTileAt(new Position(0, 1)).getTypeString(), is("hill"));
  }

  @Test
  public void mountainTileAtPos2_2() {
    assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is("mountain"));
  }

  @Test
  public void defaultTileShouldBePlains() {
    assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is("plains"));
    assertThat(game.getTileAt(new Position(5, 5)).getTypeString(), is("plains"));
  }

  @Test
  public void shouldBeRedArcherAtPos2_0() {
    assertThat(game.getUnitAt(new Position(2, 0)).getTypeString(), is("archer"));
    assertThat(game.getUnitAt(new Position(2, 0)).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldBeBlueLegionAtPos3_2() {
    assertThat(game.getUnitAt(new Position(3, 2)).getTypeString(), is("legion"));
    assertThat(game.getUnitAt(new Position(3, 2)).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldBeRedSettlerAtPos4_3() {
    assertThat(game.getUnitAt(new Position(4, 3)).getTypeString(), is("settler"));
    assertThat(game.getUnitAt(new Position(4, 3)).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldAlwaysBeBlueAfterRed() {
    // It's red's turn
    game.endOfTurn(); // Red ends his turn
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    game.endOfTurn(); // Blue ends his turn
    // It's red's turn
    game.endOfTurn(); // Red ends his turn
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }


  @Test
  public void shouldAlwaysBeRedAfterBlue() {
    // It's red's turn
    game.endOfTurn(); // Red ends his turn
    game.endOfTurn(); // Blue ends his turn
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.endOfTurn(); // Red ends his turn
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    game.endOfTurn(); // Blue ends his turn
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
    endRound();
    assertThat(game.getAge(), is(-4000 + 100));
  }


  @Test
  public void redShouldWinGameAt3000BC(){
    // End round 10 times (= we advance 1000 years)
    for (int i=0; i<10; i++)
      endRound();

    assertThat(game.getAge(), is(-4000 + 10*100)); // = -3000
    assertThat(game.getWinner(), is(Player.RED));
  }

  @Test
  public void shouldNotBeAWinnerBefore3000BC(){
    assertThat(game.getWinner(), is(nullValue()));
    game.setAge(-3100);
    assertThat(game.getWinner(), is(nullValue()));
  }


  @Test
  public void shouldMoveUnitOneTileHorizontal(){
    Position startPos = new Position(2, 0);
    Position endPos = new Position(2, 1);
    assertThat(game.getUnitAt(startPos).getTypeString(), is("archer"));
    game.moveUnit(startPos, endPos);
    assertThat(game.getUnitAt(endPos).getTypeString(), is("archer"));
  }

  @Test
  public void shouldMoveUnitOneTileVertical(){
    Position startPos = new Position(2, 0);
    Position endPos = new Position(3, 0);
    assertThat(game.getUnitAt(startPos).getTypeString(), is("archer"));
    game.moveUnit(startPos, endPos);
    assertThat(game.getUnitAt(endPos).getTypeString(), is("archer"));
  }

  @Test
  public void shouldMoveUnitOneTileDiagonal(){
    Position startPos = new Position(2, 0);
    Position endPos = new Position(3, 1);
    assertThat(game.getUnitAt(startPos).getTypeString(), is("archer"));
    game.moveUnit(startPos, endPos);
    assertThat(game.getUnitAt(endPos).getTypeString(), is("archer"));
  }

  @Test
  public void unitCanOnlyMoveOncePrRound(){
    // Positions
    Position startPos = new Position(2, 0);
    Position endPos = new Position(3, 1);
    // Red archer is at 2, 0
    assertThat(game.getUnitAt(startPos).getTypeString(), is("archer"));
    // Unit moves and method returns true
    assertTrue(game.moveUnit(startPos, endPos));
    // Unit has moved
    assertThat(game.getUnitAt(endPos).getTypeString(), is("archer"));
    // Unit cannot be moved again so method returns false
    assertFalse(game.moveUnit(endPos, new Position(4, 1)));
  }

  @Test
  private void unitShouldOnlyMoveOneTile(){

  }


}
