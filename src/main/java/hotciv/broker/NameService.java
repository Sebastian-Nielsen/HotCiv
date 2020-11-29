package hotciv.broker;

public interface NameService {

	public void put(String objectId, Object object);

	public Object get(String objectId);

//	public Unit getUnit(String objectId);
//
//	public City getCity(String objectId);
//
//	public Tile getTile(String objectId);
}
