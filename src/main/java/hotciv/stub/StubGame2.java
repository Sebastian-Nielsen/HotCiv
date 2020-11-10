package hotciv.stub;

import hotciv.common.CityImpl;
import hotciv.common.UnitImpl;
import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

/** Test stub for game for visual testing of
 * minidraw based graphics.

 */

public class StubGame2 implements Game {

	// === Unit handling ===
	private Position pos_archer_red;
	private Position pos_legion_blue;
	private Position pos_settler_blue;
	private Position pos_thetaciv_unit;
	private Position pos_city_red;

	private City red_city;
	private City blue_city_created_by_settler;

	private Unit red_archer;

	public Unit getUnitAt(Position p) {
		if ( p.equals(pos_archer_red) ) {
			return red_archer;
		}
		if ( p.equals(pos_settler_blue) ) {
			return new StubUnit( GameConstants.SETTLER, Player.BLUE );
		}
		if ( p.equals(pos_legion_blue) ) {
			return new StubUnit( GameConstants.LEGION, Player.BLUE );
		}
		if ( p.equals(pos_thetaciv_unit) ) {
			return new StubUnit( ThetaConstants.CARAVAN, Player.RED );
		}
		return null;
	}

	// Stub only allows moving red archer
	public boolean moveUnit( Position from, Position to ) {
		System.out.println( "-- StubGame2 / moveUnit called: "+from+"->"+to );
		if ( from.equals(pos_archer_red) ) {
			pos_archer_red = to;
		}
		// notify our observer(s) about the changes on the tiles
		gameObserver.worldChangedAt(from);
		gameObserver.worldChangedAt(to);
		return true;
	}
//
//		private boolean isValidUnitMove( Position from, Position to ) {
//		UnitImpl fromUnit = (UnitImpl) getUnitAt(from);
//		UnitImpl toUnit   = (UnitImpl) getUnitAt(to);
//
//		// A unit should only be able to move 1 tile at a time
//		boolean moveIsFurtherThan1Tile = calcDistance(from, to) > 1;
//		// If unit has less than 1 moves left
//		boolean hasTooFewMovesLeft = fromUnit.getMovesLeft() < 1;
//		if (moveIsFurtherThan1Tile || hasTooFewMovesLeft)
//			return false;
//
//		// If the unit at to-position is an ally-unit
//		boolean isAllyUnitAtToPos = toUnit != null &&
//									toUnit.getOwner() == fromUnit.getOwner();
//		if (isAllyUnitAtToPos)
//			return false;
//
//		if (! isOccupiableTile(to, fromUnit.getTypeString()))
//			return false;
//
//		// If the unit at from-position is not an ally unit
//		boolean isFromUnitAnEnemyUnit = fromUnit.getOwner() != playerInTurn;
//		if (isFromUnitAnEnemyUnit)
//			return false;
//
//		return true;
//	}
//
//	public boolean moveUnit( Position from, Position to ) {
//
//		if (! isValidUnitMove(from, to))
//			return false;
//
//		if (! didAttack)
//			updateUnitPos(from, to);
//
//		notifyObservers(o -> {
//			o.worldChangedAt(from);
//			o.worldChangedAt(to);
//		});
//		return true;
//	}

	// === Turn handling ===
	private Player inTurn;
	public void endOfTurn() {
		System.out.println( "-- StubGame2 / endOfTurn called." );
		inTurn = (getPlayerInTurn() == Player.RED ?
				Player.BLUE :
				Player.RED );
		// no age increments
		gameObserver.turnEnds(inTurn, -4000);
	}
	public Player getPlayerInTurn() { return inTurn; }


	// === Observer handling ===
	protected GameObserver gameObserver;
	// observer list is only a single one...
	public void addObserver(GameObserver observer) {
		gameObserver = observer;
	}

	public StubGame2() {
		defineWorld(1);
		// AlphaCiv configuration
		pos_archer_red = new Position( 2, 0);
		pos_legion_blue = new Position( 3, 2);
		pos_settler_blue = new Position( 4, 3);
		pos_thetaciv_unit = new Position( 6, 4);
		pos_city_red = new Position(1, 1);

		// Cities
		red_city = new CityImpl(Player.RED);

		// the only one I need to store for this stub
		red_archer = new StubUnit( GameConstants.ARCHER, Player.RED );

		inTurn = Player.RED;

	}

	// A simple implementation to draw the map of DeltaCiv
	protected Map<Position,Tile> world;
	public Tile getTileAt( Position p ) { return world.get(p); }

	/** define the world.
	 * @param worldType 1 gives one layout while all other
	 * values provide a second world layout.
	 */
	protected void defineWorld(int worldType) {
		world = new HashMap<Position,Tile>();
		for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
			for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
				Position p = new Position(r,c);
				world.put( p, new StubTile(GameConstants.PLAINS));
			}
		}
		// Creaate a little area around the theta unit of special terrain
		world.put(new Position(6,4), new StubTile(ThetaConstants.DESERT));
		world.put(new Position(6,5), new StubTile(ThetaConstants.DESERT));
		world.put(new Position(7,4), new StubTile(ThetaConstants.DESERT));
	}

	public City getCityAt( Position p ) {
		if ( p.equals(pos_city_red) ) {
			return red_city;
		}
		if (blue_city_created_by_settler != null &&
			p.equals(pos_settler_blue)) {
			return blue_city_created_by_settler;
		}
		return null;
	}
	public Player getWinner() { return null; }
	public int getAge() { return -5000; }
	public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
	public void changeProductionInCityAt( Position p, String unitType ) {}

	public void performUnitActionAt( Position p ) {
		if (p.equals(pos_settler_blue)) {
			blue_city_created_by_settler = new CityImpl(Player.BLUE);
			gameObserver.worldChangedAt(p);
		}
	}

	public void setTileFocus(Position position) {
		// TODONE: setTileFocus implementation pending.
		gameObserver.tileFocusChangedAt(position);
		System.out.println("-- StubGame2 / setTileFocus called.");
		System.out.println(" *** IMPLEMENTATION PENDING ***");
	}
}

class StubUnit extends UnitImpl {
	private String type;
	private Player owner;
	public StubUnit(String type, Player owner) {
		super(owner);
		this.type = type;
		this.owner = owner;
	}
	@Override
	public String getTypeString() { return type; }
	@Override
	public Player getOwner() { return owner; }
	@Override
	public int getMoveCount() { return 1; }
	@Override
	public int getDefensiveStrength() { return 0; }
	@Override
	public int getAttackingStrength() { return 0; }
}
