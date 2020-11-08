package hotciv.common;

import hotciv.common.concreteUnits.ArcherUnit;
import hotciv.framework.*;

import java.util.Collection;
import java.util.Map.Entry;

import static hotciv.framework.GameConstants.*;
import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;


/** Skeleton implementation of HotCiv.
 */

public class GameImpl implements Game {
	private Player playerInTurn = RED;
	private int age = -4000;

	private int successfulAttacksThisTurn = 0; // reset at the end of each turn

	private final int[][] adjacentDeltaPositions = {{0,0}, {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}}; // includes the center

	private final World world;

	private final WorldLayoutStrategy worldLayoutStrategy;
	private final AgingStrategy agingStrategy;
	private final WinnerStrategy winnerStrategy;
	private final SettlerActionStrategy settlerActionStrategy;
	private ArcherActionStrategy archerActionStrategy;
	private AttackStrategy attackStrategy;

	private int roundNumber = 1;


	public GameImpl(GameFactory gameFactory) {
		// Initialize strategies
		this.agingStrategy = gameFactory.createAgingStrategy();
		this.winnerStrategy = gameFactory.createWinnerStrategy();
		this.settlerActionStrategy = gameFactory.createSettlerActionStrategy();
		this.archerActionStrategy = gameFactory.createArcherActionStrategy();
		this.worldLayoutStrategy = gameFactory.createWorldLayoutStrategy();
		this.attackStrategy = gameFactory.createAttackStrategy();
		// Initialize world
		world = new World();
		// Initialize tiles and cities
		this.worldLayoutStrategy.generateWorld(world);
	}

	public int getRoundNumber() {
		return roundNumber;
	}
	public Tile getTileAt(Position p) {
		return world.getTileAt(p);
	}
	public Unit getUnitAt( Position p ) { return world.getUnitAt(p); }
	public City getCityAt( Position p ) { return world.getCityAt(p); }
	public Player getPlayerInTurn() { return playerInTurn; }
	public Player getWinner() {
		return winnerStrategy.determineWinner(this);
	}
	public int getAge() { return age; }
	public Collection<City> getAllCities(){
		return world.getAllCities();
	}

	/**
	 * Checks whether the unit-move is valid
	 * precondition: A unit is positioned at the from-position
	 * @param from from-position where the unit is positioned
	 * @param to   to-position
	 * @return whether the move is valid
	 */
	private boolean isValidUnitMove( Position from, Position to ) {
		UnitImpl fromUnit = (UnitImpl) getUnitAt(from);
		UnitImpl toUnit   = (UnitImpl) getUnitAt(to);

		// A unit should only be able to move 1 tile at a time
		boolean moveIsFurtherThan1Tile = calcDistance(from, to) > 1;
		// If unit has less than 1 moves left
		boolean hasTooFewMovesLeft = fromUnit.getMovesLeft() < 1;
		if (moveIsFurtherThan1Tile || hasTooFewMovesLeft)
			return false;

		// If the unit at to-position is an ally-unit
		boolean isAllyUnitAtToPos = toUnit != null &&
									toUnit.getOwner() == fromUnit.getOwner();
		if (isAllyUnitAtToPos)
			return false;

		if (! isOccupiableTile(to, fromUnit.getTypeString()))
			return false;

		// If the unit at from-position is not an ally unit
		boolean isFromUnitAnEnemyUnit = fromUnit.getOwner() != playerInTurn;
		if (isFromUnitAnEnemyUnit)
			return false;

		return true;
	}

	public boolean moveUnit( Position from, Position to ) {

		if (! isValidUnitMove(from, to))
			return false;

		boolean didAttack = attackUnit(from, to);
		if (! didAttack)
			updateUnitPos(from, to);

		postMoveUnitSideEffects(to);
		return true;
	}



	/**
	 * @param from position
	 * @param to position
	 * @return whether a unit was attacked
	 */
	private boolean attackUnit(Position from, Position to) {
		boolean isUnitAtToPos = getUnitAt(to) != null;

		// If there is no unit to attack => no unit was attacked
		if (! isUnitAtToPos)
			return false;

		// At this point, we know a unit is attacked for sure

		boolean wasAttackSuccessful = attackStrategy.attackUnit(from, to, this);

		if (wasAttackSuccessful)
			successfulAttacksThisTurn++;

		return true;
	}


	public void updateUnitPos(Position from, Position to) {
		world.createUnitAt(to, popUnitAt(from));
	}

	public void preMoveUnitSideEffects(Position to) {
		// Remove enemy unit (if any)
		popUnitAt(to);
	}

	public void postMoveUnitSideEffects(Position to) {
		UnitImpl unitGettingMoved = (UnitImpl) getUnitAt(to);

		// Update moves left of the unit getting moved by decreasing its movecount by 1
		updateMovesLeft(unitGettingMoved, unitGettingMoved.getMovesLeft() - 1);

		// Conquer city if city present at to-pos
		Player newOwner = unitGettingMoved.getOwner();
		conquerCity(to, newOwner);
	}

	/**
	 * If city is present at the given position,
	 * change the ownership of the city
	 * @param pos The position to check for a city at
	 * @param newOwner The new owner of the city
	 */
	private void conquerCity(Position pos, Player newOwner) {
		// If a city is at the position
		if (isCityAtPos(pos)) {
			// Update ownership of city
			City city = getCityAt(pos);
			updateCityOwnership(city, newOwner);
		}
	}

	private void updateCityOwnership(City city, Player newOwner) {
		CityImpl cityImpl = (CityImpl) city;
		cityImpl.setOwner(newOwner);
	}

	private void updateMovesLeft(Unit u, int value) {
		UnitImpl unit = (UnitImpl) u;
		unit.setMovesLeft(value);
	}


	private boolean isCityAtPos(Position pos) {
		return getCityAt(pos) != null;
	}

	/**
	 * Removes and returns the unit at the give pos from the world
	 * @param pos unit's position
	 * @return The unit that is removed
	 */
	public Unit popUnitAt(Position pos) {
		return world.popUnitAt(pos);
	}



	private int calcDistance(Position from, Position to) {
		return Math.max(Math.abs(from.getColumn() - to.getColumn()),
		                Math.abs(from.getRow() - to.getRow()));
	}

	private void endOfRoundEffects() {
		// Increment age
		incrementAge();
		// Increment round number
		incrementRoundNumber();
		// Increment production by CITY_PRODUCTION_INCREMENT_RATE in all cities
		incrementProductionInAllCities(CITY_PRODUCTION_INCREMENT_RATE);
		// Reset units' moves left
		resetMovesLeftOfAllUnits();
		// Spawn units for each city that has enough production
		spawnUnitsForAllCitiesWherePossible();
	}

	private void incrementRoundNumber() {
		roundNumber++;
	}

	/**
	 * Spawn units for all cities where possible
	 */
	private void spawnUnitsForAllCitiesWherePossible() {
		// For all cities
		for (Entry<Position, City> entry : world.getSetOfPosCityEntry()) {

			// Current city
			Position cityPos =            entry.getKey();
			CityImpl city    = (CityImpl) entry.getValue();

			// If city can spawn unit
			if (canCitySpawnUnit(city, cityPos)) {
				// Deduct unit cost for city
				deductUnitCostFromCitysTreasury(city);
				// Spawn unit
				spawnUnitForCity(city, cityPos);
			}
		}
	}

	/**
	 * Precondition: The city has enough treasury to buy the unit
	 * Purchase the unit that the city has focused production against
	 * @param city The city to buy the unit for
	 * // TODO this method could be made more general along the lines of 'deductCostOfFocusedProductionFromCitysTreasury', but at the moment, only units can be set as city production focus, so no need
	 */
	private void deductUnitCostFromCitysTreasury(CityImpl city) {
		int unitCost = getCitysProductionCost(city);
		// Deduct unit cost from treasury
		deductFromCitysTreasury(city, unitCost);
	}

	/**
	 * @param city The city of which to get the production cost xfor
	 * @return The cost of the unit the city is producing against
	 */
	private int getCitysProductionCost(CityImpl city) {
		return getCostOfUnit(city.getProduction());
	}

	/**
	 * Spawn the unit the given city has focused production against
	 * @param city    City to spawn unit for
	 * @param cityPos Position of the city
	 */
	private void spawnUnitForCity(City city, Position cityPos) {
		String unitType = city.getProduction();
		Player owner = city.getOwner();

		spawnUnitAtPos(
				getFirstEmptyAndOccupiableAdjacentTile(cityPos, unitType),
				unitType, owner
		);
	}

	/**
	 * Deducts a given amount from the treasury of the city
	 * @param city   The city of which treasury should get deducted
	 * @param amount The amount to deduct
	 */
	private void deductFromCitysTreasury(CityImpl city, int amount) {
		city.setTreasury(city.getTreasury() - amount);
	}

	/**
	 * The city can spawn a unit if the following conditions are meet:
	 * (1) It has accumulated enough treasury in comparison to the cost
	 *     of the production focus.
	 * (2) There is an empty and occpiable tile adjacent to the city
	 *     to spawn the unit at.
	 * @param city    The city
	 * @param cityPos The position of the city
	 * @return Wether the city can spawn a unit
	 */
	private boolean canCitySpawnUnit(CityImpl city, Position cityPos) {
 		// Get the cost of the unit the city is producing
		int unitCost = getCitysProductionCost(city);

		boolean hasAccumulatedEnoughTreasury = city.getTreasury() >= unitCost;
		if (! hasAccumulatedEnoughTreasury)
			return false;

		if (! isEmptyAndOccupiableAdjacentTile(cityPos, city.getProduction()))
			// No empty and occupiable tile to spawn unit at
			return false;

		return true;
	}

	/**
	 * @param pos The position which adjacent tiles is checked.
	 * @return Whether there is an empty and occupiable adjacent tile,
	 * or the center tile of the given position is empty and occupiable.
	 */
	private boolean isEmptyAndOccupiableAdjacentTile(Position pos, String unitType) {
		return getFirstEmptyAndOccupiableAdjacentTile(pos, unitType) != null;
	}

	/**
	 * Resets the moves left count for each unit
	 */
	private void resetMovesLeftOfAllUnits() {
		for (Unit u : world.getAllUnits()){
			UnitImpl unit = (UnitImpl) u;
			unit.resetMovesLeft();
		}
	}

	private void incrementProductionInAllCities(int amount) {
		world.getAllCities().forEach(c -> {
			CityImpl cityImpl = (CityImpl) c;
			cityImpl.setTreasury(cityImpl.getTreasury() + amount);
		});
	}

	/**
	 * Increments the age in accordance to the given aging strategy
	 */
	private void incrementAge() {
		setAge(agingStrategy.incrementAge(age));
	}

	/**
	 * Spawns a unit at the given position
	 * @param pos Position to spawn unit at
	 * @param unitType The type of unit to spawn
	 * @param owner Owner of the unit
	 */
	private void spawnUnitAtPos(Position pos, String unitType, Player owner) {
		world.spawnUnitAtPos(pos, unitType, owner);
	}

	/**
	 * @param unitType The type of the unit
	 * @param pos  The position of the tile
	 * @return Whether the tile is occupiable by the given unit
	 */
	private Boolean isOccupiableTile(Position pos, String unitType) {
		return ((TileImpl) getTileAt(pos)).canUnitTraverse(unitType);
	}

	/** Returns the first empty (no other unit standing on it) AND
	 * occupiable (tile is walkable) adjacent to, or on, the given
	 * position starting from the center tile, then north and then clockwise
	 * @param pos center position
	 * @param unitType
	 * @return the first empty and occupiable tile
	 *         or null if none is present
	 */
	private Position getFirstEmptyAndOccupiableAdjacentTile(Position pos, String unitType){
		for (int[] deltaPos : adjacentDeltaPositions) {
			// Find possible position for the unit to spawn at
			Position unitPos = new Position(pos.getRow()    + deltaPos[0],
			                                pos.getColumn() + deltaPos[1]);

			// Check whether there isn't a unit on the tile and the tile is occupiable
			if (!world.isUnitAtPos(unitPos) && isOccupiableTile(unitPos, unitType)) {
				// An empty tile has been found, so we are finished
				return unitPos;
			}
		}
		return null;
	}

	private int getCostOfUnit(String unitType) {
		switch (unitType) {
			case ARCHER: return ARCHER_COST;
			case LEGION: return LEGION_COST;
			case SETTLER: return SETTLER_COST;
			case CARAVAN: return CARAVAN_COST;
		}
		throw new RuntimeException("Unrecognized unitType " + unitType);
	}

	public void endOfTurn() {
		endOfTurnEffects();
		switch (playerInTurn) {
			case RED:
				playerInTurn = BLUE;
				break;
			case BLUE:
				playerInTurn = RED;
				endOfRoundEffects();
				break;
		}
	}

	private void endOfTurnEffects() {
		// See if there is any winner
		winnerStrategy.determineWinner(this);
		// Reset success
		ResetSuccessfulAttacksThisTurn();
	}

	private void ResetSuccessfulAttacksThisTurn() {
		successfulAttacksThisTurn = 0;
	}

	public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
	public void changeProductionInCityAt( Position p, String unitType ) {
		CityImpl city = (CityImpl) getCityAt(p);
		city.setProduction(unitType);

	}
	public void performUnitActionAt( Position pos ) {
		boolean isSettlerAtPos = getTypeOfUnitAt(pos).equals(SETTLER);
		if (isSettlerAtPos) {
			settlerActionStrategy.performAction(this, pos);
			return;
		}

		boolean isArcherAtPos = getTypeOfUnitAt(pos).equals(ARCHER);
		if (isArcherAtPos) {
			archerActionStrategy.performAction((ArcherUnit) getUnitAt(pos));
			return;
		}

		((UnitImpl) getUnitAt(pos)).performAction(this, pos);

	}

	private GameObserver gameObserver;
	@Override
	public void addObserver(GameObserver observer) {
		gameObserver = observer;
	}


	@Override
	public void setTileFocus(Position position) {
		gameObserver.tileFocusChangedAt(position);
	}


	public boolean isUnitAtPos(Position pos) {return world.isUnitAtPos(pos);}

	/**
	 * Get type of unit at the given position
	 * @param pos The position of the unit
	 * @return The type of the unit; e.g. archer
	 */
	private String getTypeOfUnitAt(Position pos) {
		return getUnitAt(pos).getTypeString();
	}

	public void setAge(int newAge){
		age = newAge;
	}

	public void createCityAt(Position pos, CityImpl city) {
		world.createCityAt(pos, city);
	}


	/**
	 * Create a tile at the given position
	 * @param pos Position to create tile at
	 * @param tile Tile to create
	 */
	public void createTileAtPos(Position pos, TileImpl tile) {
		world.createTileAtPos(pos, tile);
	}

	/**
	 * Create a city at the given position
	 * @param pos Position to create city at
	 * @param city City to create
	 */
	public void createCityAtPos(Position pos, CityImpl city) {
		world.createCityAt(pos, city);
	}

	public int getSuccessfulAttacksThisTurn() {
		return successfulAttacksThisTurn;
	}
}
