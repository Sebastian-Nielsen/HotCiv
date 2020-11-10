package hotciv.common.GameFactories;

import hotciv.common.CityImpl;
import hotciv.common.TrueRandomNumberStrategy;
import hotciv.common.agingStrategies.ProgressiveAgingStrategy;
import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
import hotciv.common.attackStrategies.CombinedStrengthAttackStrategy;
import hotciv.common.settlerActionStrategies.BuildCitySettlerActionStrategy;
import hotciv.common.winnerStrategies.ThreeSuccessfulAttacksWinnerStrategy;
import hotciv.common.worldLayoutStrategies.CustomWorldLayoutStrategy;
import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

import static hotciv.framework.Player.BLUE;
import static hotciv.framework.Player.RED;

public class SemiCivFactory implements GameFactory {

	private Map<Position, Unit> posToUnits;
	private Map<Position, City> posToCities;
	private String[] layout;

	public SemiCivFactory(String[] layout, Map<Position, City> posToCities, Map<Position, Unit> posToUnits) {
		this.layout = layout;
		this.posToCities = posToCities;
		this.posToUnits = posToUnits;
	}

	/* Constructor with default map */
	public SemiCivFactory() {
		// Init layout
		this.layout =
				new String[]{
						"...ooMooooo.....",
						"..ohhoooofffoo..",
						".oooooMooo...oo.",    // '.' is 'ocean'
						".ooMMMoooo..oooo",    // 'o' is 'plains'
						"...ofooohhoooo..",    // 'M' is 'mountain'
						".ofoofooooohhoo.",    // 'f' is 'forest'
						"...ooo..........",    // 'h' is 'hills'
						".ooooo.ooohooM..",
						".ooooo.oohooof..",
						"offfoooo.offoooo",
						"oooooooo...ooooo",
						".ooMMMoooo......",
						"..ooooooffoooo..",
						"....ooooooooo...",
						"..ooohhoo.......",
						".....ooooooooo..",
				};;
		// Init posToCities
		this.posToCities = new HashMap<>();
		posToCities.put(new Position(8, 12), new CityImpl(RED));
		posToCities.put(new Position(4,  5), new CityImpl(BLUE));

		// Init posToUnits
		this.posToUnits = new HashMap<>();
	}

	@Override
	public WorldLayoutStrategy createWorldLayoutStrategy() {
		return new CustomWorldLayoutStrategy(layout, posToCities, posToUnits);
	}

	@Override
	public WinnerStrategy createWinnerStrategy() {
		return new ThreeSuccessfulAttacksWinnerStrategy();
	}

	@Override
	public AgingStrategy createAgingStrategy() {
		return new ProgressiveAgingStrategy();
	}

	@Override
	public AttackStrategy createAttackStrategy() {
		return new CombinedStrengthAttackStrategy(new TrueRandomNumberStrategy());
	}


	@Override
	public ArcherActionStrategy createArcherActionStrategy() {
		return new NoArcherActionStrategy();
	}

	@Override
	public SettlerActionStrategy createSettlerActionStrategy() {
		return new BuildCitySettlerActionStrategy();
	}
}


