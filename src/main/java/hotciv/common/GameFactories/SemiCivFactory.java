package hotciv.common.GameFactories;

import hotciv.common.TrueRandomNumberStrategy;
import hotciv.common.agingStrategies.LinearAgingStrategy;
import hotciv.common.agingStrategies.ProgressiveAgingStrategy;
import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
import hotciv.common.attackStrategies.AttackerAlwaysWinsAttackStrategy;
import hotciv.common.attackStrategies.CombinedStrengthAttackStrategy;
import hotciv.common.settlerActionStrategies.BuildCitySettlerActionStrategy;
import hotciv.common.settlerActionStrategies.NoSettlerActionStrategy;
import hotciv.common.winnerStrategies.AlternatingWinnerStrategy;
import hotciv.common.winnerStrategies.ThreeSuccessfulAttacksWinnerStrategy;
import hotciv.common.worldLayoutStrategies.AlphaCivWorldLayoutStrategy;
import hotciv.common.worldLayoutStrategies.DeltaCivWorldLayoutStrategy;
import hotciv.framework.*;

public class SemiCivFactory implements GameFactory {

	private String[] layout;

	public SemiCivFactory(String[] layout) {
		this.layout = layout;
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
	public WorldLayoutStrategy createWorldLayoutStrategy() {
		return new DeltaCivWorldLayoutStrategy(layout);
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


