package hotciv.testStubs;

import hotciv.common.CityImpl;
import hotciv.common.UnitImpl;
import hotciv.common.concreteTiles.MountainsTile;
import hotciv.framework.*;
import hotciv.stub.StubTile;
import hotciv.stub.ThetaConstants;

import java.util.HashMap;
import java.util.Map;

import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;

/** Test stub for Distribution testing

 */

public class StubGame3 implements Game {

	// === Unit handling ===
	private Position pos_archer_red;
	private Position pos_legion_blue;
	private Position pos_settler_blue;
	private Position pos_thetaciv_unit;
	private Position pos_city_red;

	private City red_city;
	private City blue_city_created_by_settler;

	private StubUnit red_archer;

	public Unit getUnitAt(Position p) {
		if ( p.equals(pos_archer_red) ) {
			return red_archer;
		}
		if ( p.equals(pos_settler_blue) ) {
			return new StubUnit( GameConstants.SETTLER, BLUE,"blueSettler");
		}
		if ( p.equals(pos_legion_blue) ) {
			return new StubUnit( GameConstants.LEGION, BLUE, "blueLegion");
		}
		if ( p.equals(pos_thetaciv_unit) ) {
			return new StubUnit( ThetaConstants.CARAVAN, RED, "redCaravan");
		}
		return null;
	}

	// Stub only allows moving red archer
	public boolean moveUnit( Position from, Position to ) {
		System.out.println( "-- StubGame3 / moveUnit called: "+from+"->"+to );
		if ( from.equals(pos_archer_red) ) {
			pos_archer_red = to;
		}
		// notify our observer(s) about the changes on the tiles
		// gameObserver.worldChangedAt(from);
		// gameObserver.worldChangedAt(to);
		return true;
	}

	// === Turn handling ===
	private Player inTurn;
	public void endOfTurn() {
		System.out.println( "-- StubGame3 / endOfTurn called." );
		inTurn = (getPlayerInTurn() == RED ?
				BLUE :
				RED );
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

	public StubGame3() {
		defineWorld(1);
		// AlphaCiv configuration
		pos_archer_red = new Position( 2, 0);
		pos_legion_blue = new Position( 3, 2);
		pos_settler_blue = new Position( 4, 3);
		pos_thetaciv_unit = new Position( 6, 4);
		pos_city_red = new Position(1, 1);

		// Cities
		red_city = new CityImpl(RED);

		// the only one I need to store for this stub
		red_archer = new StubUnit( GameConstants.ARCHER, RED, "redArcher" );

		inTurn = RED;

	}

	// A simple implementation to draw the map of DeltaCiv
	protected Map<Position,Tile> world;
	public Tile getTileAt( Position p ) { return new MountainsTile(); }

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
		// Create a little area around the theta unit of special terrain
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

	private Player winner;

	public void setWinner(Player newWinner) {
		winner = newWinner;
	}
	public Player getWinner() {
		return winner;
	}

	public int getAge() { return -4000; }
	public void changeWorkForceFocusInCityAt( Position p, String balance ) {  }
	public void changeProductionInCityAt( Position p, String unitType ) { ((CityImpl) getCityAt(p)).setProduction(unitType); }

	public void performUnitActionAt( Position p ) {
		if (p.equals(pos_settler_blue)) {
			blue_city_created_by_settler = new CityImpl(BLUE);
			gameObserver.worldChangedAt(p);
		}
	}

	public void setTileFocus(Position position) {
		// TODONE: setTileFocus implementation pending.
		gameObserver.tileFocusChangedAt(position);
		System.out.println("-- StubGame3 / setTileFocus called.");
		System.out.println(" *** IMPLEMENTATION PENDING ***");
	}
}

class StubUnit extends UnitImpl {
	private String type;
	private Player owner;
	private String id;

	public StubUnit(String type, Player owner, String id) {
		super(owner);
		this.type = type;
		this.owner = owner;
		this.id = id;
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
