package hotciv.broker;

public interface NameService {

	public void put(String objectId, Object object);

	public Object get(String objectId);

}
