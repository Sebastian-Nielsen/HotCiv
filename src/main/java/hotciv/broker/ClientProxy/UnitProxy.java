package hotciv.broker.ClientProxy;

import frds.broker.Requestor;
import hotciv.broker.OperationNames;
import hotciv.framework.Unit;
import hotciv.framework.Player;

public class UnitProxy implements Unit {
	private final Requestor requestor;

	private String HOTCIV_OBJECTID = " idk man ";

	public UnitProxy(Requestor requestor) {
		this.requestor = requestor;
	}

	@Override
	public String getTypeString() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_UNIT_TYPESTRING,
				String.class);
	}

	@Override
	public Player getOwner() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_UNIT_OWNER,
				Player.class);
	}

	@Override
	public int getMoveCount() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_MOVE_COUNT,
				Integer.class);
	}

	@Override
	public int getDefensiveStrength() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_DEFENSIVE_STRENGTH,
				Integer.class);
	}

	@Override
	public int getAttackingStrength() {
		return requestor.sendRequestAndAwaitReply(
				HOTCIV_OBJECTID, OperationNames.GET_ATTACKING_STRENGTH,
				Integer.class);
	}
}
