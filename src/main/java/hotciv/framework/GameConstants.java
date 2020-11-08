package hotciv.framework;

/** Collection of constants used in HotCiv Game. Note that strings are
 * used instead of enumeration types to keep the set of valid
 * constants open to extensions by future HotCiv variants.  Enums can
 * only be changed by compile time modification.
*/
public class GameConstants {
  // The size of the world is set permanently to a 16x16 grid 
  public static final int WORLDSIZE = 16;
  // Valid unit types
  public static final String ARCHER    = "archer";
  public static final String LEGION    = "legion";
  public static final String SETTLER   = "settler";
  public static final String CARAVAN   = "caravan";
  // Unit costs
  public static final int ARCHER_COST  = 10;
  public static final int LEGION_COST  = 15;
  public static final int SETTLER_COST = 30;
  public static final int CARAVAN_COST = 30;
  // Unit travel distance
  public static final int ARCHER_TRAVEL_DISTANCE = 1;
  public static final int LEGION_TRAVEL_DISTANCE = 1;
  public static final int SETTLER_TRAVEL_DISTANCE = 1;
  public static final int CARAVAN_TRAVEL_DISTANCE = 2;
  // Attack strength
  public static final int ARCHER_ATTACK_STRENGTH = 2;
  public static final int LEGION_ATTACK_STRENGTH = 4;
  public static final int SETTLER_ATTACK_STRENGTH = 0;
  public static final int CARAVAN_ATTACK_STRENGTH = 1;
  // Defensive strength
  public static final int ARCHER_DEFENSIVE_STRENGTH = 3;
  public static final int LEGION_DEFENSIVE_STRENGTH = 2;
  public static final int SETTLER_DEFENSIVE_STRENGTH = 3;
  public static final int CARAVAN_DEFENSIVE_STRENGTH = 4;

  // Valid terrain types
  public static final String PLAINS    = "plains";
  public static final String OCEANS    = "ocean";
  public static final String FOREST    = "forest";
  public static final String HILLS     = "hills";
  public static final String MOUNTAINS = "mountain";
  public static final String DESERT    = "desert";
  // Valid production balance types
  public static final String productionFocus = "hammer";
  public static final String foodFocus = "apple";
  // How much the city's production should incremnt at the end of each round
  public static final int CITY_PRODUCTION_INCREMENT_RATE = 6;
  public static final int CARAVAN_SIZE_ACTION_INCREASE = 2;
}
