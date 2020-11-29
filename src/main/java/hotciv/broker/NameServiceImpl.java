package hotciv.broker;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NameServiceImpl implements NameService {


	private final HashMap<String, Object> nameServer;

	public NameServiceImpl() {
		this.nameServer = new HashMap<>();
	}

	@Override
	public void put(String objectId, Object object) {
		nameServer.put(objectId, object);
	}

	@Override
	public Object get(String objectId) {
		return nameServer.get(objectId);
	}



//	@Override
//	public Unit getUnit(String objectId) {
//		return (Unit) nameServer.getOrDefault(objectId, null);
//	}
//
//	@Override
//	public City getCity(String objectId) {
//		return (City) nameServer.getOrDefault(objectId, null);
//	}
//
//	@Override
//	public Tile getTile(String objectId) {
//		return (Tile) nameServer.getOrDefault(objectId, null);
//	}

}
