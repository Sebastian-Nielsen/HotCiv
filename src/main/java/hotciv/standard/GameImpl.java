package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

import static hotciv.framework.GameConstants.*;
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
    if (unitToMovesLeft.get(fromUnit) < calcDistance(from, to))
      return false;

    // If Unit at to-position is an ally-unit
    if (getUnitAt(to) != null &&
        getUnitAt(to).getOwner() == fromUnit.getOwner())
      return false;

    // Units cannot move to "mountain" tile
    if (getTileAt(to).getTypeString().equals("mountain"))
      return false;

    // A player cannot move other player's units
    if (fromUnit.getOwner() != playerInTurn)
      return false;

    return true;
  }

  public boolean moveUnit( Position from, Position to ) {
    Unit unit = posToUnits.get(from);

    // If the move isn't valid return false
    if (!isValidUnitMove(from, to))
      return false;

      /* Update the move left count of units */
    // The unit is destroyed so remove it from the Map
    unitToMovesLeft.remove(getUnitAt(to));
    // Update moves left
    unitToMovesLeft.put(unit, 0);

       /* Move the unit */
    // Update unit's position
    posToUnits.remove(from);
    posToUnits.put(to, unit);

    return true;
  }



  private int calcDistance(Position from, Position to) {
    return Math.max(Math.abs(from.getColumn() - to.getColumn()),
                    Math.abs(from.getRow() - to.getRow()));
  }

  /* Mutator methods */
  private void endOfRoundEffects() {
    /* Increment age by 100 years ***********************/
    setAge(getAge() + 100);
    /* Increment production by 6 in all cities **********/
    posToCity.values().forEach(c -> {
      CityImpl cityImpl = (CityImpl) c;
      cityImpl.setTreasury(cityImpl.getTreasury() + 6);
    });
    /* Reset moves left *********************************/
    for (Unit p : posToUnits.values()){
      unitToMovesLeft.put(p, p.getMoveCount());
    }

    for (Map.Entry<Position, City> entry : posToCity.entrySet()) {
        Position spawnPos = entry.getKey();
        CityImpl city = (CityImpl) entry.getValue();

        // Get unit-cost
        int unitCost = getCostOfUnit(city.getProduction());

        // If the city has accumulated enough treasury
        if (city.getTreasury() >= unitCost) {
          // Spawn unit
          spawnUnitAtPos(spawnPos);
          // Deduct unit cost
          city.setTreasury(city.getTreasury() - unitCost);
        }
    }
  }

  /**
   * Spawns a unit at the given position
   * @param pos Position to spawn unit at
   */
  private void spawnUnitAtPos(Position pos) {
    CityImpl city = (CityImpl) posToCity.get(pos);
    posToUnits.put(
            pos,
            new UnitImpl(city.getProduction(), city.getOwner())
    );
  }

  private int getCostOfUnit(String unitType) {
    switch (unitType) {
      case ARCHER: return ARCHER_COST;
      case LEGION: return LEGION_COST;
      case SETTLER: return SETTLER_COST;
    }
    throw new RuntimeException("Unrecognized unitType " + unitType);
  }

  public void endOfTurn() {
    switch (playerInTurn) {
      case RED:
        playerInTurn = BLUE;
        break;
      case BLUE:
        playerInTurn = RED;
        endOfRoundEffects();
        break;
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}
  public void setAge(int newAge){
    age = newAge;
  }
}
