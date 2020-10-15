package hotciv.common.GameFactories;

import hotciv.common.TrueRandomNumberStrategy;
import hotciv.common.agingStrategies.ProgressiveAgingStrategy;
import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
import hotciv.common.attackStrategies.CombinedStrengthAttackStrategy;
import hotciv.common.settlerActionStrategies.BuildCitySettlerActionStrategy;
import hotciv.common.winnerStrategies.ThreeSuccessfulAttacksWinnerStrategy;
import hotciv.common.worldLayoutStrategies.CustomWorldLayoutStrategy;
import hotciv.framework.*;

import java.util.Map;

public class SemiCivFactory implements GameFactory {

	private Map<Position, City> posToCities;
	private String[] layout;

	public SemiCivFactory(String[] layout, Map<Position, City> posToCities) {
		this.layout = layout;
		this.posToCities = posToCities;
	}

	@Override
	public WorldLayoutStrategy createWorldLayoutStrategy() {
		return new CustomWorldLayoutStrategy(layout, posToCities);
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


