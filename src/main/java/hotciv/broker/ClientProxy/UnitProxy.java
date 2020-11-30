package hotciv.broker.ClientProxy;

import frds.broker.Requestor;
import hotciv.broker.Constants.*;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitProxy implements Unit {
	private final Requestor requestor;
	private final String id;

	public UnitProxy(Requestor requestor, String id) {
		this.requestor = requestor;
		this.id = id;
	}

	@Override
	public String getTypeString() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_UNIT_TYPESTRING,
				String.class);
	}

	@Override
	public Player getOwner() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_UNIT_OWNER,
				Player.class);
	}

	@Override
	public int getMoveCount() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_MOVE_COUNT,
				Integer.class);
	}

	@Override
	public int getDefensiveStrength() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_DEFENSIVE_STRENGTH,
				Integer.class);
	}

	@Override
	public int getAttackingStrength() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_ATTACKING_STRENGTH,
				Integer.class);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getMovesLeft() {
		return requestor.sendRequestAndAwaitReply(
				id, OperationNames.GET_MOVES_LEFT,
				Integer.class);
	}
}
