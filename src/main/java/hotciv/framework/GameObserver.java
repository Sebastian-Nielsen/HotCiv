package hotciv.framework;

public interface GameObserver {
	/** invoked every time some change occurs on a position
	 * in the world - a unit disappears or appears, a
	 * city appears, a city changes player color, or any
	 * other event that requires the GUI to redraw the
	 * graphics on a particular position.
	 * @param pos the position in the world that has changed state
	 */
	public void worldChangedAt(Position pos);

	/** invoked just after the game's end of turn is called
	 * to signal the new "player in turn" and world age state.
	 * @param nextPlayer the next player that may move units etc.
	 * @param age the present age of the world
	 */
	public void turnEnds(Player nextPlayer, int age);

	/** invoked whenever the user changes focus to another
	 * tile (for inspecting the tile's unit and city
	 * properties.)
	 * @param position the position of the tile that is
	 * now inspected/has focus.
	 */
	public void tileFocusChangedAt(Position position);

	public void requestUpdate();
}
