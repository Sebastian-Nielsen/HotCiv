package hotciv.broker;

import hotciv.framework.Unit;

import java.util.HashMap;

public class NameServerImpl implements NameServer {


	private final HashMap<String, Object> nameServer;

	public NameServerImpl() {
		this.nameServer = new HashMap<>();
	}

	@Override
	public void put(String objectId, Object object) {
		nameServer.put(objectId, object);
	}

	@Override
	public Unit getUnit(String objectId) {
		return (Unit) nameServer.getOrDefault(objectId, null);
	}

}
