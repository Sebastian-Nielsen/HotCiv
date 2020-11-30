package hotciv.framework;

/** Tile represents a single territory tile of a given type.

    Responsibilities:
    1) Know its type.
 */

public interface Tile {

  /** return the tile type as a string. The set of
   * valid strings are defined by the graphics
   * engine, as they correspond to named image files.
   * @return the type type as string
   */
  public String getTypeString();

  public String getId();
}
