package hotciv.framework;

/** Represents a single unit in the game.

Responsibilities:
1) Know its type name.
2) Know its owner.
3) Know its defensive and attacking strengths.
*/
public interface Unit {

  /** return the type of the unit
   * @return unit type as a string, valid values are at
   * least those listed in GameConstants, particular variants
   * may define more strings to be valid.
   */
  public String getTypeString();

  /** return the owner of this unit.
   * @return the player that controls this unit.
   */
  public Player getOwner();

  /** return the move distance left (move count).
   * A move count of N means the unit may travel
   * a distance of N in this turn. The move count
   * is reset to the units maximal value before
   * a new turn starts.
   * @return the move count
   */
  public int getMoveCount();
  
  /** return the defensive strength of this unit
   * @return defensive strength
   */
  public int getDefensiveStrength();
  
  /** return the attack strength of this unit
   * @return attack strength
   */
  public int getAttackingStrength();

  public String getId();

  public int getMovesLeft();

}
