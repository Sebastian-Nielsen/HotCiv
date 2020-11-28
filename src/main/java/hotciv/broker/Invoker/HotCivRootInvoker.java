package hotciv.common.unitTests;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.RequestObject;
import hotciv.broker.Invoker.HotCivCityInvoker;
import hotciv.broker.Invoker.HotCivGameInvoker;
import hotciv.broker.Invoker.HotCivTileInvoker;
import hotciv.broker.Invoker.HotCivUnitInvoker;
import hotciv.broker.OperationNames;
import hotciv.framework.Game;

import java.util.HashMap;
import java.util.Map;

public class HotCivRootInvoker implements Invoker {
	private final Map<String, Invoker> invokerMap;
	private final Gson gson;

	public HotCivRootInvoker(Game servant) {
		this.gson = new Gson();
		invokerMap = new HashMap<>();
		invokerMap.put(OperationNames.GAME_PREFIX, new HotCivGameInvoker(servant));
		invokerMap.put(OperationNames.UNIT_PREFIX, new HotCivUnitInvoker());
		invokerMap.put(OperationNames.CITY_PREFIX, new HotCivCityInvoker());
		invokerMap.put(OperationNames.TILE_PREFIX, new HotCivTileInvoker());
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
