package hotciv.framework;

import hotciv.common.GameImpl;
import hotciv.common.World;

public class GameImplTranscriptDecorator implements Game {
	private final Game game;
	private StringBuilder transcript;

	public GameImplTranscriptDecorator(Game game) {
		this.game = game;
		transcript = new StringBuilder();
	}

	public String getTranscript() {
		return transcript.toString();
	}

	@Override
	public Tile getTileAt(Position p) {
		transcript
				.append("Gets tile at pos ")
				.append(p)
				.append("\n");
		return game.getTileAt(p);
	}

	@Override
	public Unit getUnitAt(Position p) {
		transcript
				.append("Gets unit at pos ")
				.append(p)
				.append("\n");
		return game.getUnitAt(p);
	}

	@Override
	public City getCityAt(Position p) {
		transcript
				.append("Gets city at pos ")
				.append(p)
				.append("\n");
		return game.getCityAt(p);
	}

	@Override
	public Player getPlayerInTurn() {
		Player player = game.getPlayerInTurn();
		transcript
				.append("Get player in turn: ")
				.append(player)
				.append("\n");
		return player;
	}

	@Override
	public Player getWinner() {
		Player winner = game.getWinner();
		transcript
				.append("Get winner: ")
				.append(winner)
				.append("\n");
		return winner;
	}

	@Override
	public int getAge() {
		int age = game.getAge();
		transcript
				.append("Get age: ")
				.append(age)
				.append("\n");
		return age;
	}

	@Override
	public boolean moveUnit(Position from, Position to) {
		transcript
				.append("Moving unit from pos ")
				.append(from)
				.append(" to pos ")
				.append(to)
				.append(".\n");
		return game.moveUnit(from, to);
	}

	@Override
	public void endOfTurn() {
		Player playerInTurn = game.getPlayerInTurn();
		transcript
				.append(playerInTurn)
				.append(" ends turn.\n");
		game.endOfTurn();
	}

	@Override
	public void changeWorkForceFocusInCityAt(Position p, String balance) {
		City city = game.getCityAt(p);
		transcript
				.append("Change workforce focus in city ")
				.append(city)
				.append(" to ")
				.append(balance)
				.append(".\n");
		game.changeWorkForceFocusInCityAt(p, balance);
	}

	@Override
	public void changeProductionInCityAt(Position p, String unitType) {
		City city = game.getCityAt(p);
		transcript
				.append("Change production in city ")
				.append(city)
				.append(" to ")
				.append(unitType)
				.append(".\n");
		game.changeProductionInCityAt(p, unitType);
	}

	@Override
	public void performUnitActionAt(Position p) {
		transcript
				.append("Perform unit action at pos ")
				.append(p)
				.append(".\n");
		game.performUnitActionAt(p);
	}
//
//	@Override
//	public void setAge(int newAge) {
//		int oldAge = game.getAge();
//		transcript
//			.append("Set age from ")
//			.append(oldAge)
//			.append(" to ")
//			.append(newAge)
//			.append(".\n");
//		game.setAge(newAge);
//	}
//
//	@Override
//	public void updateUnitPos(Position from, Position to) {
//		transcript
//			.append("Update unit pos from ")
//			.append(from)
//			.append(" to pos ")
//			.append(to)
//			.append(".\n");
//		game.updateUnitPos(from, to);
//	}
//
//	@Override
//	public Unit popUnitAt(Position to) {
//		transcript
//			.append("Pop unit at pos ")
//			.append(to)
//			.append(".\n");
//		return game.popUnitAt(to);
//	}
//
//	@Override
//	public boolean isUnitAtPos(Position pos) {
//		transcript
//			.append("Is ")
//			.append(to)
//			.append(".\n");
//		return game.isUnitAtPos(pos);
//	}


}
