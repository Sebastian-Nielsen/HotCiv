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
  private Map<Position, City> posToCity = new HashMap<>();

  /* Accessor methods */
  public GameImpl() {
    // Initialize cities
    posToCity.put(new Position(1, 1), new CityImpl(Player.RED));
    posToCity.put(new Position(4, 1), new CityImpl(Player.BLUE));
  }

  public Tile getTileAt( Position p ) { return null; }
  public Unit getUnitAt( Position p ) { return null; }
  public City getCityAt( Position p ) { return posToCity.getOrDefault(p, null); }
  public Player getPlayerInTurn() { return playerInTurn; }
  public Player getWinner() { if (age < -3000) return null; else return RED; }
  public int getAge() { return age; }
  public boolean moveUnit( Position from, Position to ) {
    return false;
  }

  /* Mutator methods */
  private void endOfRoundEffects() {
    // Increment age by 100 years
    setAge(getAge() + 100);
    // Increment production by 6 in all cities
    posToCity.values().forEach(c -> c.setTreasury(c.getTreasury() + 6));

  }

  public void endOfTurn() {
    switch (playerInTurn) {
      case RED:    playerInTurn = BLUE; break;
      case BLUE:   playerInTurn = YELLOW; break;
      case YELLOW: playerInTurn = GREEN; break;
      case GREEN:  playerInTurn = RED; endOfRoundEffects(); break;
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}
  public void setAge(int newAge){
    age = newAge;
  }
}
