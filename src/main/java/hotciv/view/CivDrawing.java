package hotciv.view;

import hotciv.common.CityImpl;
import hotciv.common.UnitImpl;
import hotciv.framework.*;
import hotciv.view.figure.CityFigure;
import hotciv.view.figure.HotCivFigure;
import hotciv.view.figure.TextFigure;
import hotciv.view.figure.UnitFigure;
import minidraw.framework.*;
import minidraw.standard.ImageFigure;
import minidraw.standard.StandardDrawing;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** CivDrawing is a specialized Drawing (MVC model component) from
 * MiniDraw that dynamically builds the list of Figures for MiniDraw
 * to render the Unit and other information objects that are visible
 * in the Game instance.
 *
 * TODO: This is a TEMPLATE for the SWEA Exercise! This means
 * that it is INCOMPLETE and that there are several options
 * for CLEANING UP THE CODE when you add features to it!
 */

public class CivDrawing
		implements Drawing, GameObserver {

	protected Drawing delegate;
	/** store all moveable figures visible in this drawing = units */
	protected Map<Unit, UnitFigure> unitFigureMap;

	/** store all moveable figures visible in this drawing = cities */
	private Map<City, CityFigure> cityFigureMap;

	/** the Game instance that this CivDrawing is going to render units from */
	protected Game game;

	public CivDrawing( DrawingEditor editor, Game game ) {
		super();
		this.delegate = new StandardDrawing();
		this.game = game;
		this.unitFigureMap = new HashMap<>();
		this.cityFigureMap = new HashMap<>();

		// register this unit drawing as listener to any game state changes...
		game.addObserver(this);
		// ... and build up the set of figures associated with
		// units in the game.
		defineUnitMap();
		// And also cities
		defineCityMap();
		// and the set of 'icons' in the status panel
		defineIcons();
	}

	/** The CivDrawing should not allow client side
	 * units to add and manipulate figures; only figures
	 * that renders game objects are relevant, and these
	 * should be handled by observer events from the game
	 * instance. Thus this method is 'killed'.
	 */
	public Figure add(Figure arg0) {
		throw new RuntimeException("Should not be used...");
	}


	/** erase the old list of units, and build a completely new
	 * one from scratch by iterating over the game world and add
	 * Figure instances for each unit in the world.
	 */
	protected void defineUnitMap() {
		// ensure no units of the old list are accidental in
		// the selection!
		clearSelection();

		// remove all unit figures in this drawing
		removeAllUnitFigures();

		// iterate world, and create a unit figure for
		// each unit in the game world, as well as
		// create an association between the unit and
		// the unitFigure in 'unitFigureMap'.
		Position p;
		for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
			for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
				p = new Position(r,c);
				Unit unit = game.getUnitAt(p);
				if ( unit != null ) {
					String type = unit.getTypeString();
					// convert the unit's Position to (x,y) coordinates
					Point point = new Point( GfxConstants.getXFromColumn(p.getColumn()),
							GfxConstants.getYFromRow(p.getRow()) );
					UnitFigure unitFigure =
							new UnitFigure( type, point, unit );
					unitFigure.addFigureChangeListener(this);
					unitFigureMap.put(unit, unitFigure);

					// also insert in delegate list as it is
					// this list that is iterated by the
					// graphics rendering algorithms
					delegate.add(unitFigure);
				}
			}
		}
	}


	protected void defineCityMap() {
		// ensure no city of the old list are accidental in the selection!
		clearSelection();

		// remove all city figures in this drawing
		removeAllCityFigures();

		// iterate world, and create a city figure for
		// each city in the game world, as well as
		// create an association between the city and
		// the cityFigure in 'cityFigureMap'.
		Position p;
		for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
			for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
				p = new Position(r,c);
				City city = game.getCityAt(p);
				if ( city != null ) {
					// convert the city's Position to (x,y) coordinates
					Point point = new Point( GfxConstants.getXFromColumn(p.getColumn()),
							GfxConstants.getYFromRow(p.getRow()) );
					CityFigure cityFigure = new CityFigure(city, point);
					cityFigure.addFigureChangeListener(this);
					cityFigureMap.put(city, cityFigure);

					// also insert in delegate list as it is
					// this list that is iterated by the
					// graphics rendering algorithms
					delegate.add(cityFigure);
				}
			}
		}
	}

	/** remove all unit figures in this
	 * drawing, and reset the map (unit->unitfigure).
	 * It is important to actually remove the figures
	 * as it forces a graphical redraw of the screen
	 * where the unit figure was.
	 */
	protected void removeAllUnitFigures() {
		for (Unit u : unitFigureMap.keySet()) {
			UnitFigure uf = unitFigureMap.get(u);
			delegate.remove(uf);
		}
		unitFigureMap.clear();
	}

	/** remove all city figures in this
	 * drawing, and reset the map (city->cityfigure).
	 * It is important to actually remove the figures
	 * as it forces a graphical redraw of the screen
	 * where the city figure was.
	 */
	protected void removeAllCityFigures() {
		for (City c : cityFigureMap.keySet()) {
			CityFigure cf = cityFigureMap.get(c);
			delegate.remove(cf);
		}
		cityFigureMap.clear();
	}

	protected ImageFigure turnShieldIcon;
	protected ImageFigure unitShieldIcon;
	protected ImageFigure cityShieldIcon;
	protected ImageFigure cityProductionIcon;
	protected ImageFigure cityWorkforceIcon;
	protected TextFigure unitMovesLeftText;
	protected TextFigure ageText;


	protected void defineIcons() {
		// TODONE: Further development to include rest of figures needed

		turnShieldIcon =
				new HotCivFigure("redshield",
						new Point( GfxConstants.TURN_SHIELD_X,
								GfxConstants.TURN_SHIELD_Y ),
						GfxConstants.TURN_SHIELD_TYPE_STRING);
		updateTurnShield(game.getPlayerInTurn());
		// insert in delegate figure list to ensure graphical
		// rendering.
		delegate.add(turnShieldIcon);


		ageText = new TextFigure("4000 BC",
				new Point(GfxConstants.AGE_TEXT_X,
						GfxConstants.AGE_TEXT_Y) );
		updateAgeText(game.getAge());
		delegate.add(ageText);

	}

	// === Observer Methods ===

	public void worldChangedAt(Position pos) {
		// TODONE: Remove system.out debugging output
		// System.out.println( "CivDrawing: world changes at "+pos);

		// this is a really brute-force algorithm: destroy
		// all known units and build up the entire set again
		defineUnitMap();
		defineCityMap();

		// TODONE: Cities may change on position as well
	}

	@Override
	public void requestUpdate() {
		// A request has been issued to repaint
		// everything. We simply rebuild the
		// entire Drawing.
		defineUnitMap();
		defineCityMap();
		defineIcons();

		// TODONE: Cities pending
	}

	public void turnEnds(Player nextPlayer, int age) {
		// TODONE: Remove system.out debugging output
		// System.out.println( "CivDrawing: turnEnds for "+
		//                    nextPlayer+" at "+age );

		updateTurnShield(nextPlayer);

		// TODONE: Age output pending
		updateAgeText(age);


	}

	private void updateAgeText(int age) {
		String ageSuffix = " AC";
		if (age < 0) { ageSuffix = " BC"; }

		ageText.setText("" + Math.abs(age) + ageSuffix);
	}

	private void updateTurnShield(Player nextPlayer) {
		turnShieldIcon.set( convertToOwnerShield(nextPlayer),
				new Point( GfxConstants.TURN_SHIELD_X,
						GfxConstants.TURN_SHIELD_Y ) );
	}

	private String convertToOwnerShield(Player owner) {
		return owner.toString().toLowerCase() + "shield";
	}

	public void clearPanel() {
		clearUnitSectionInPanel();
		clearCitySectionInPanel();
	}

	public void clearCitySectionInPanel() {
		remove(cityShieldIcon);
		remove(cityProductionIcon);
		remove(cityWorkforceIcon);
	}

	public void clearUnitSectionInPanel() {
		remove(unitMovesLeftText);
		remove(unitShieldIcon);
	}

	public void tileFocusChangedAt(Position position) {
		UnitImpl unitAtPos = (UnitImpl) game.getUnitAt(position);
		CityImpl cityAtPos = (CityImpl) game.getCityAt(position);
		boolean isUnitAtPos = unitAtPos != null;
		boolean isCityAtPos = cityAtPos != null;

		clearPanel(); // Clear all icons in the panel

		if (isUnitAtPos)
			createUnitSectionInPanel(unitAtPos);

		if (isCityAtPos)
			createCitySectionInPanel(cityAtPos);
	}

	private void createCitySectionInPanel(CityImpl cityAtPos) {
		createCityOwnerIcon(cityAtPos);
		createCityProductionIcon(cityAtPos);
		createCityWorkforceIcon(cityAtPos);
	}

	public void createUnitSectionInPanel(UnitImpl unitAtPos) {
		createUnitShield(unitAtPos);
		createUnitMovesLeftText(unitAtPos);
	}

	private void createUnitMovesLeftText(UnitImpl unitAtPos) {
		unitMovesLeftText = new TextFigure("0",
				new Point(GfxConstants.UNIT_COUNT_X,
						GfxConstants.UNIT_COUNT_Y) );
		updateAgeText(unitAtPos.getMovesLeft());
		delegate.add(unitMovesLeftText);
	}

	private void createCityWorkforceIcon(CityImpl cityAtPos) {
		cityWorkforceIcon =
				new HotCivFigure( cityAtPos.getWorkforceFocus() ,
						new Point( GfxConstants.WORKFORCEFOCUS_X,
								GfxConstants.WORKFORCEFOCUS_Y ),
						GfxConstants.WORKFORCE_TYPE_STRING);
		delegate.add(cityWorkforceIcon);
	}

	private void createCityOwnerIcon(City cityAtPos) {
		cityShieldIcon =
				new HotCivFigure( convertToOwnerShield(cityAtPos.getOwner()),
						new Point( GfxConstants.CITY_SHIELD_X,
								GfxConstants.CITY_SHIELD_Y ),
						GfxConstants.CITY_TYPE_STRING);
		delegate.add(cityShieldIcon);
	}

	private void createCityProductionIcon(City cityAtPos) {
		cityProductionIcon =
				new HotCivFigure( cityAtPos.getProduction() ,
						new Point( GfxConstants.CITY_PRODUCTION_X,
								GfxConstants.CITY_PRODUCTION_Y ),
						GfxConstants.UNIT_TYPE_STRING);
		delegate.add(cityProductionIcon);
	}

	private void createUnitShield(Unit unitAtPos) {
		unitShieldIcon =
				new HotCivFigure( convertToOwnerShield(unitAtPos.getOwner()),
						new Point(GfxConstants.UNIT_SHIELD_X,
								GfxConstants.UNIT_SHIELD_Y ),
						GfxConstants.UNIT_SHIELD_TYPE_STRING);
		delegate.add(unitShieldIcon);
	}


	@Override
	public void addToSelection(Figure arg0) {
		delegate.addToSelection(arg0);
	}

	@Override
	public void clearSelection() {
		delegate.clearSelection();
	}

	@Override
	public void removeFromSelection(Figure arg0) {
		delegate.removeFromSelection(arg0);
	}

	@Override
	public List<Figure> selection() {
		return delegate.selection();
	}

	@Override
	public void toggleSelection(Figure arg0) {
		delegate.toggleSelection(arg0);
	}

	@Override
	public void figureChanged(FigureChangeEvent arg0) {
		delegate.figureChanged(arg0);
	}

	@Override
	public void figureInvalidated(FigureChangeEvent arg0) {
		delegate.figureInvalidated(arg0);
	}

	@Override
	public void figureRemoved(FigureChangeEvent arg0) {
		delegate.figureRemoved(arg0);
	}

	@Override
	public void figureRequestRemove(FigureChangeEvent arg0) {
		delegate.figureRequestRemove(arg0);
	}

	@Override
	public void figureRequestUpdate(FigureChangeEvent arg0) {
		delegate.figureRequestUpdate(arg0);
	}

	@Override
	public void addDrawingChangeListener(DrawingChangeListener arg0) {
		delegate.addDrawingChangeListener(arg0);
	}

	@Override
	public void removeDrawingChangeListener(DrawingChangeListener arg0) {
		delegate.removeDrawingChangeListener(arg0);
	}

	@Override
	public Figure findFigure(int arg0, int arg1) {
		return delegate.findFigure(arg0, arg1);
	}

	@Override
	public Iterator<Figure> iterator() {
		return delegate.iterator();
	}

	@Override
	public void lock() {
		delegate.lock();
	}

	@Override
	public Figure remove(Figure arg0) {
		return delegate.remove(arg0);
	}

	@Override
	public void unlock() {
		delegate.unlock();
	}
}
