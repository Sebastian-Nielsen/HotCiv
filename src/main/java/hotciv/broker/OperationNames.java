package hotciv.broker;

/**
 * The names of the valid operations (i.e. method calls) in the HotCiv system.
 */
public class OperationNames {
  // Method names are prefixed with the type of the method receiver ('hotciv') which
  // can be used in when serveral different types of objects are present at the server side
  // and is also helpful in case of failure on the server side where log files can be
  // inspected.

  public static final String SEPERATOR = "_";

  // === Type prefixes
  public static final String GAME_PREFIX = "game";
  public static final String UNIT_PREFIX = "unit";
  public static final String CITY_PREFIX = "city";
  public static final String TILE_PREFIX = "tile";

  // === Operation names

  // Game
  public static final String GET_WINNER                        = GAME_PREFIX + SEPERATOR + "get-winner";
  public static final String GET_TILE_AT                       = GAME_PREFIX + SEPERATOR + "get-tile-at";
  public static final String GET_UNIT_AT                       = GAME_PREFIX + SEPERATOR + "get-unit-at";
  public static final String GET_CITY_AT                       = GAME_PREFIX + SEPERATOR + "get-city-at";
  public static final String END_OF_TURN                       = GAME_PREFIX + SEPERATOR + "end-of-turn";
  public static final String GET_PLAYER_IN_TURN                = GAME_PREFIX + SEPERATOR + "get-player-in-turn";
  public static final String GET_AGE                           = GAME_PREFIX + SEPERATOR + "get-age";
  public static final String MOVE_UNIT                         = GAME_PREFIX + SEPERATOR + "move-unit";
  public static final String CHANGE_WORKFORCE_FOCUS_IN_CITY_AT = GAME_PREFIX + SEPERATOR + "change-workforce-focus-in-city-at";
  public static final String CHANGE_PRODUCTION_IN_CITY_AT      = GAME_PREFIX + SEPERATOR + "change-production-in-city-at";
  public static final String PERFORM_UNIT_ACTION_AT            = GAME_PREFIX + SEPERATOR + "perform-unit-action-at";

  // City
  public static final String GET_CITY_OWNER      = CITY_PREFIX + SEPERATOR + "get-owner";
  public static final String GET_SIZE            = CITY_PREFIX + SEPERATOR + "get-size";
  public static final String GET_TREASURY        = CITY_PREFIX + SEPERATOR + "get-treasury";
  public static final String GET_PRODUCTION      = CITY_PREFIX + SEPERATOR + "get-production";
  public static final String GET_WORKFORCE_FOCUS = CITY_PREFIX + SEPERATOR + "get-workforce-focus";

  // Unit
  public static final String GET_UNIT_TYPESTRING    = UNIT_PREFIX + SEPERATOR + "get-typestring";
  public static final String GET_UNIT_OWNER         = UNIT_PREFIX + SEPERATOR + "get-owner";
  public static final String GET_MOVE_COUNT         = UNIT_PREFIX + SEPERATOR + "get-movecount";
  public static final String GET_DEFENSIVE_STRENGTH = UNIT_PREFIX + SEPERATOR + "get-defensive-strength";
  public static final String GET_ATTACKING_STRENGTH = UNIT_PREFIX + SEPERATOR + "get-attacking-strength";

  // Tile
  public static final String GET_TILE_TYPESTRING = TILE_PREFIX + SEPERATOR + "get-typestring";



}