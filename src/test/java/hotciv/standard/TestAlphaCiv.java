package hotciv.standard;

import hotciv.framework.*;

import org.junit.jupiter.api.*;

import static hotciv.framework.GameConstants.ARCHER;
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
    Position newEndPos = new Position(4, 1);
    assertFalse(game.moveUnit(endPos, newEndPos));
    // End the round
    endRound();
    // The unit should now be able to move
    assertTrue(game.moveUnit(endPos, newEndPos));
  }

  @Test
  public void unitShouldOnlyMoveOneTilePrMove(){
    // Positions
    Position startPos = new Position(2, 0);
    Position endPos = new Position(4, 0);
    // Move unit 2 tiles
    assertFalse(game.moveUnit(startPos, endPos));
  }

  @Test
  public void unitCannotMoveToTileOccupiedByAllyUnit() {
    Position fromPos = new Position(2, 0);

    Unit redArcher = game.getUnitAt(fromPos);

    game.moveUnit(fromPos, new Position(3, 1));
    endRound();

    game.moveUnit(new Position(3, 1), new Position(4, 2));
    endRound();

    assertFalse(
            game.moveUnit(new Position(4, 2), new Position(4, 3))
    );
  }

  @Test
  public void unitCannotMoveOverMountainTile() {
    Position fromPos = new Position(2, 0);

    Unit redArcher = game.getUnitAt(fromPos);

    game.moveUnit(fromPos, new Position(2, 1));
    endRound();

    assertFalse(
            game.moveUnit(new Position(2, 1), new Position(2, 2))
    );
  }

  @Test
  public void redCannotMoveBluesUnit() {
    Position fromPos = new Position(3, 2);
    Position toPos = new Position(3, 3);

    Unit blueLegion = game.getUnitAt(fromPos);

    assertFalse(
            game.moveUnit(fromPos, toPos),
            "It's red's turn; therefore he cannot move the blue unit"
    );
  }

  @Test
  public void redShouldAttackAndDestroyBluesUnit() {
    Position fromPos = new Position(2, 0);
    Position toPos = new Position(3, 2);

    Unit redArcher = game.getUnitAt(fromPos);

    game.moveUnit(fromPos, new Position(3, 1));
    endRound();

    assertThat(
            "A blue unit should be at position (3,2)",
            game.getUnitAt(toPos).getOwner(), is(Player.BLUE)
    );

    game.moveUnit(new Position(3, 1), toPos);

    assertThat(
            "Red should destroy and move to the to-position",
            game.getUnitAt(new Position(3, 2)), is(redArcher)
    );
  }

  @Test
  public void redProducesAnArcherFor10Production() {
    assertThat(
            "By default the production focus is on 'archer'",
            redCity.getProduction(), is("archer")
    );

    // Treasury is 0 to begin
    endRound(); // Treasury should increase by 6
    // Treasury is 6
    endRound(); // Treasury should increase by 6
    // Treasury is 12

    assertThat(
            "The cost of the archer (10) is deducted from the treasury",
            redCity.getTreasury(), is(12 - 10)
    );
    assertThat(
            "An archer should spawn at the city's location",
            game.getUnitAt(new Position(1, 1)).getTypeString(), is("archer")
    );
  }



  @Test
  public void unitShouldSpawnNorthCityIfOccupiedByUnit(){
    endRound();
    endRound(); // New unit is spawned in the city and treasury is at 2
    endRound();
    endRound(); // New unit is spawned north of the city and treasury is at 4
    assertThat(
            "The cost of the archer (10) is deducted from the treasury twice",
            redCity.getTreasury(), is((6*4) - (10*2))
    );
    assertThat(
            "An archer should spawn north of the city",
            game.getUnitAt(new Position(0, 1)).getTypeString(), is("archer")
    );
  }


  @Test
  public void unitShouldSpawnAroundTheCityIfCityIsOccupied(){
    endRound();
    endRound(); // New unit is spawned in the city and treasury is at 2
    endRound();
    endRound(); // New unit is spawned north of the city and treasury is at 4
    endRound();
    endRound(); // New unit is spawned in the city and treasury is at 6
    // Tiles north of city (0,1) is occupied
    assertThat(
            "An archer should spawn north-east of the city",
            game.getUnitAt(new Position(0, 2)).getTypeString(), is("archer")
    );
  }

}
