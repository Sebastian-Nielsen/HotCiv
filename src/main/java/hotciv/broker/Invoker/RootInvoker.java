package hotciv.broker.Invoker;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.RequestObject;
import hotciv.broker.NameService;
import hotciv.broker.OperationNames;
import hotciv.framework.Game;

import java.util.HashMap;
import java.util.Map;

public class RootInvoker implements Invoker {
	private final Map<String, Invoker> invokerMap;
	private final Gson gson;

	public RootInvoker(Game servant, NameService ns) {
		this.gson = new Gson();
		invokerMap = new HashMap<>();
		invokerMap.put(OperationNames.GAME_PREFIX, new HotCivGameInvoker(servant, ns));
		invokerMap.put(OperationNames.UNIT_PREFIX, new UnitInvoker(ns));
		invokerMap.put(OperationNames.CITY_PREFIX, new CityInvoker(ns));
		invokerMap.put(OperationNames.TILE_PREFIX, new TileInvoker(ns));
	}

	@Override
	public String handleRequest(String request) {
		// Demarshall the requestObject in order to retrieve the operationName,
		// which we need in order to know which specific invoker to delegate to
		RequestObject requestObject = gson.fromJson(request, RequestObject.class);
		String operationName = requestObject.getOperationName();

		// Identify the subinvoker to call using the operationName
		String type = operationName.substring(0, operationName.indexOf(OperationNames.SEPERATOR));
		Invoker subInvoker = invokerMap.get(type);

		return subInvoker.handleRequest(request);
	}
}
