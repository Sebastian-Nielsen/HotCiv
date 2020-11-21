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


}