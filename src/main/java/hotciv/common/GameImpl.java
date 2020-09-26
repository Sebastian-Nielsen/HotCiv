package hotciv.common;

import hotciv.framework.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static hotciv.framework.GameConstants.*;
import static hotciv.framework.Player.*;


/** Skeleton implementation of HotCiv.
*/

public class GameImpl implements Game {
  private Player playerInTurn = RED;
  private int age = -4000;
  private final Map<Unit, Integer> unitToMovesLeft = new HashMap<>();
  private final int[][] adjacentDeltaPositions = {{0,0}, {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1} ,{-1,-1}};

  private final World world;

  private final WorldLayoutStrategy worldLayoutStrategy;
  private final AgingStrategy agingStrategy;
  private final WinnerStrategy winnerStrategy;
  private final SettlerActionStrategy settlerActionStrategy;
  private ArcherActionStrategy archerActionStrategy;

  /* Accessor methods */
  public GameImpl(AgingStrategy agingStrategy,
                  WinnerStrategy winnerStrategy,
                  SettlerActionStrategy settlerActionStrategy,
                  ArcherActionStrategy noArcherActionStrategy,
                  WorldLayoutStrategy worldLayoutStrategy,
                  String[] layout) {
    // Initialize world
    world = new World();

    // Initialize strategies
    this.agingStrategy = agingStrategy;
    this.winnerStrategy = winnerStrategy;
    this.settlerActionStrategy = settlerActionStrategy;
    this.archerActionStrategy = noArcherActionStrategy;
    this.worldLayoutStrategy = worldLayoutStrategy;
    // Initialize tiles and cities
    this.worldLayoutStrategy.generateWorld(world, layout);
    // Initialize units
    Unit redArcher = new UnitImpl("archer", RED);
    Unit blueLegion = new UnitImpl("legion", BLUE);
    Unit redSettler = new UnitImpl("settler", RED);
    // Initialize units' positions
    world.createUnitAt(new Position(2, 0), redArcher);
    world.createUnitAt(new Position(3, 2), blueLegion);
    world.createUnitAt(new Position(4, 3), redSettler);
    // Initialize units' moves left
    unitToMovesLeft.put(redArcher, redArcher.getMoveCount());
    unitToMovesLeft.put(blueLegion, blueLegion.getMoveCount());
    unitToMovesLeft.put(redSettler, redSettler.getMoveCount());
  }

  public Tile getTileAt(Position p) {
    return world.getTileAt(p);
  }
  public Unit getUnitAt( Position p ) { return world.getUnitAt(p); }
  public City getCityAt( Position p ) { return world.getCityAt(p); }
  public Player getPlayerInTurn() { return playerInTurn; }
  //public Player getWinner() { if (age < -3000) return null; else return RED; }
  public Player getWinner() { return winnerStrategy.getWinner(this); }
  public int getAge() { return age; }
  public Collection<City> getAllCities(){
    return world.getAllCities();
  }

  /**
   * Checks whether the unit-move is valid
   * precondition: A unit is positioned at the from-position
   * @param from from-position where the unit is positioned
   * @param to   to-position
   * @return whether the move is valid
   */
  private boolean isValidUnitMove( Position from, Position to ) {
    Unit fromUnit = world.getUnitAt(from);

    // If unit has less than 0 moves left
    if (unitToMovesLeft.get(fromUnit) < calcDistance(from, to))
      return false;

    // If Unit at to-position is an ally-unit
    if (getUnitAt(to) != null &&
        getUnitAt(to).getOwner() == fromUnit.getOwner())
      return false;

    // Units cannot move to "mountain" tile
    //if (getTileAt(to).getTypeString().equals("mountain"))
    if (!isOccupiableTile(to))
      return false;

    // A player cannot move other player's units
    if (fromUnit.getOwner() != playerInTurn)
      return false;

    return true;
  }

  public boolean moveUnit( Position from, Position to ) {
    Unit unit = world.getUnitAt(from);

    // If the move isn't valid return false
    if (!isValidUnitMove(from, to))
      return false;

      /* Update the move left count of units */
    // The unit is destroyed so remove it from the Map
    unitToMovesLeft.remove(getUnitAt(to));
    // Update moves left
    unitToMovesLeft.put(unit, 0);

    // Check if a city should be conquered
    CityImpl toCity = (CityImpl) getCityAt(to);
    boolean isCityAtToPos = toCity != null;
    if (isCityAtToPos) {
      toCity.setOwner(getUnitAt(from).getOwner());
    }

       /* Move the unit */
    // Update unit's position
    popUnitAt(from);
    world.createUnitAt(to, unit);


    return true;
  }

  public Unit popUnitAt(Position pos) {
    return world.popUnitAt(pos);
  }



  private int calcDistance(Position from, Position to) {
    return Math.max(Math.abs(from.getColumn() - to.getColumn()),
                    Math.abs(from.getRow() - to.getRow()));
  }

  /* Mutator methods */
  private void endOfRoundEffects() {
    /* Increment age ***********************/
    setAge(agingStrategy.incrementAge(age));
    /* Increment production by 6 in all cities **********/
    world.getAllCities().forEach(c -> {
      CityImpl cityImpl = (CityImpl) c;
      cityImpl.setTreasury(cityImpl.getTreasury() + 6);
    });
    /* Reset moves left *********************************/
    for (Unit p : world.getAllUnits()){
      unitToMovesLeft.put(p, p.getMoveCount());
    }

    // Spawn units for each city that has enough production
    for (Map.Entry<Position, City> entry : world.getSetOfPosCityEntry()) {
        Position cityPos = entry.getKey();
        CityImpl city = (CityImpl) entry.getValue();

        // Get unit-cost
        int unitCost = getCostOfUnit(city.getProduction());

        // If the city has accumulated enough treasury
        if (city.getTreasury() >= unitCost) {
          // Search for empty tile to spawn unit
          Position spawnPos = searchForEmptyAdjacentTile(cityPos);
          if (spawnPos == null)
            continue; // No empty or occupiable tile, so do nothing

          // Spawn unit
          spawnUnitAtPos(spawnPos, city.getProduction(), city.getOwner());
          // Deduct unit cost
          city.setTreasury(city.getTreasury() - unitCost);
        }
    }
  }

  /**
   * Spawns a unit at the given position
   * @param pos Position to spawn unit at
   * @param unitType The type of unit to spawn
   * @param owner Owner of the unit
   */
  private void spawnUnitAtPos(Position pos, String unitType, Player owner) {
    world.spawnUnitAtPos(pos, unitType, owner);
  }


  /**
   * @param pos The position of the tile
   * @return Whether the tile is occupiable by unit
   */
  private Boolean isOccupiableTile(Position pos){
    return !((getTileAt(pos).getTypeString().equals("ocean")) ||
             (getTileAt(pos).getTypeString().equals("mountain")));
  }

  /** Returns the first empty and occupiable adjacent to, or on, pos
   * starting from the center tile, then north and then clockwise
   * @param pos center position
   * @return the first empty and occupiable tile
   */
  private Position searchForEmptyAdjacentTile(Position pos){
    for (int[] deltaPos : adjacentDeltaPositions) {
      // Find possible position for the unit to spawn at
      Position unitPos = new Position(pos.getRow() + deltaPos[0],
              pos.getColumn() + deltaPos[1]);

      // Check whether there isn't a unit on the tile and the tile is occupiable
      if (!world.isUnitAtPos(unitPos) && isOccupiableTile(unitPos)) {
        // An empty tile has been found, so we are finished
        return unitPos;
      }
    }
    return null;
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
  public void performUnitActionAt( Position pos ) {

    boolean isSettlerAtPos = getTypeOfUnitAt(pos).equals("settler");
    if (isSettlerAtPos) {
      settlerActionStrategy.performAction(this, pos);
      return;
    }

    boolean isArcherAtPos = getTypeOfUnitAt(pos).equals("archer");
    if (isArcherAtPos) {
      UnitImpl archerUnit = (UnitImpl) getUnitAt(pos);
      archerActionStrategy.performAction( archerUnit);
    }

  }

  /**
   * Get type of unit at the given position
   * @param pos The position of the unit
   * @return The type of the unit; e.g. archer
   */
  private String getTypeOfUnitAt(Position pos) {
    return getUnitAt(pos).getTypeString();
  }

  public void setAge(int newAge){
    age = newAge;
  }

  public void createCityAt(Position pos, CityImpl city) {
    world.createCityAt(pos, city);
  }


  /**
   * Create a tile at the given position
   * @param pos Position to create tile at
   * @param tile Tile to create
   */
  public void createTileAtPos(Position pos, TileImpl tile) {
    world.createTileAtPos(pos, tile);
  }

  /**
   * Create a city at the given position
   * @param pos Position to create city at
   * @param city City to create
   */
  public void createCityAtPos(Position pos, CityImpl city) {
    world.createCityAt(pos, city);
  }
}
