package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

import static hotciv.framework.Player.*;

/** Skeleton implementation of HotCiv.
*/

public class GameImpl implements Game {
  private Player playerInTurn = RED;
  private int age = -4000;
  private final Map<Position, City> posToCity = new HashMap<>();
  private final Map<Position, Tile> posToTiles = new HashMap<>();
  private final Map<Position, Unit> posToUnits = new HashMap<>();
  private final Map<Unit, Integer> unitToMovesLeft = new HashMap<>();

  /* Accessor methods */
  public GameImpl() {
    // Initialize cities
    posToCity.put(new Position(1, 1), new CityImpl(RED));
    posToCity.put(new Position(4, 1), new CityImpl(BLUE));
    // Initialize tiles
    posToTiles.put(new Position(1, 0), new TileImpl("ocean"));
    posToTiles.put(new Position(0, 1), new TileImpl("hill"));
    posToTiles.put(new Position(2, 2), new TileImpl("mountain"));
    // Initialize units
    Unit redArcher = new UnitImpl("archer", RED);
    Unit blueLegion = new UnitImpl("legion", BLUE);
    Unit redSettler = new UnitImpl("settler", RED);
    // Initialize units' positions
    posToUnits.put(new Position(2, 0), redArcher);
    posToUnits.put(new Position(3, 2), blueLegion);
    posToUnits.put(new Position(4, 3), redSettler);
    // Initialize units' moves left
    unitToMovesLeft.put(redArcher, redArcher.getMoveCount());
    unitToMovesLeft.put(blueLegion, blueLegion.getMoveCount());
    unitToMovesLeft.put(redSettler, redSettler.getMoveCount());
  }

  public Tile getTileAt(Position p) {
    return posToTiles.getOrDefault(p, new TileImpl("plains"));
  }
  public Unit getUnitAt( Position p ) { return posToUnits.getOrDefault(p, null); }
  public City getCityAt( Position p ) { return posToCity.getOrDefault(p, null); }
  public Player getPlayerInTurn() { return playerInTurn; }
  public Player getWinner() { if (age < -3000) return null; else return RED; }
  public int getAge() { return age; }

  /**
   * Checks whether the unit-move is valid
   * precondition: A unit is positioned at the from-position
   * @param from from-position where the unit is positioned
   * @param to   to-position
   * @return whether the move is valid
   */
  private boolean isValidUnitMove( Position from, Position to ) {
    Unit fromUnit = posToUnits.get(from);

    // If unit has less than 0 moves left
    if (unitToMovesLeft.get(fromUnit) < calcDistance(from, to)) {
      return false;
    }
    // If Unit at to-position is an ally-unit
    if (getUnitAt(to) != null &&
        getUnitAt(to).getOwner() == fromUnit.getOwner()) {
      return false;
    }

    return true;
  }

  public boolean moveUnit( Position from, Position to ) {
    Unit unit = posToUnits.get(from);

    // If the move isn't valid return false
    if (!isValidUnitMove(from, to))
      return false;

    // Update unit's position
    posToUnits.remove(from);
    posToUnits.put(to, unit);
    // Update moves left
    unitToMovesLeft.put(unit, 0);
    return true;
  }



  private int calcDistance(Position from, Position to) {
    return Math.max(Math.abs(from.getColumn() - to.getColumn()),
                    Math.abs(from.getRow() - to.getRow()));
  }

  /* Mutator methods */
  private void endOfRoundEffects() {
    // Increment age by 100 years
    setAge(getAge() + 100);
    // Increment production by 6 in all cities
    posToCity.values().forEach(c -> c.setTreasury(c.getTreasury() + 6));
    // Reset moves left
    for (Unit p : posToUnits.values()){
      unitToMovesLeft.put(p, p.getMoveCount());
    }
  }

  public void endOfTurn() {
    switch (playerInTurn) {
      case RED:    playerInTurn = BLUE; break;
      case BLUE:   playerInTurn = RED; endOfRoundEffects(); break;
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}
  public void setAge(int newAge){
    age = newAge;
  }
}
