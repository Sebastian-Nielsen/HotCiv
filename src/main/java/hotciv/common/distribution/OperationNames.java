package hotciv.common.distribution;

/**
 * The names of the valid operations (i.e. method calls) in the HotCiv system.
 */
public class OperationNames {
  // Method names are prefixed with the type of the method receiver ('hotciv') which
  // can be used in when serveral different types of objects are present at the server side
  // and is also helpful in case of failure on the server side where log files can be
  // inspected.

  public static final String GET_WINNER = "hotciv-get-winner";
  public static final String GET_TILE_AT = "hotciv-get-tile-at";
  public static final String GET_UNIT_AT = "hotciv-get-unit-at";
  public static final String GET_CITY_AT = "hotciv-get-city-at";
  public static final String END_OF_TURN = "hotciv-end-of-turn";
  public static final String GET_PLAYER_IN_TURN = "hotciv-get-player-in-turn";
  public static final String GET_AGE = "hotciv-get-age";
  public static final String MOVE_UNIT = "hotciv-move-unit";
  public static final String CHANGE_WORKFORCE_FOCUS_IN_CITY_AT = "hotciv-change-workforce-focus-in-city-at";
  public static final String CHANGE_PRODUCTION_IN_CITY_AT = "hotciv-change-production-in-city-at";
  public static final String PERFORM_UNIT_ACTION_AT = "hotciv-perform-unit-action-at";

  public static final String GET_OWNER = "city-get-owner";
  public static final String GET_SIZE = "city-get-size";
  public static final String GET_TREASURY = "city-get-treasury";
  public static final String GET_PRODUCTION = "city-get-production";
  public static final String GET_WORKFORCE_FOCUS = "city-get-workforce-focus";


}