package hotciv.broker;

import hotciv.framework.Unit;

public interface NameServer {

	public void put(String objectId, Object object);

	public Unit getUnit(String objectId);



}
