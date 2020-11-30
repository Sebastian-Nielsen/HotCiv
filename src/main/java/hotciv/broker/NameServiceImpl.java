package hotciv.broker;

import java.util.HashMap;


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

}
