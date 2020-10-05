package hotciv.common.GameFactories;

import hotciv.common.agingStrategies.LinearAgingStrategy;
import hotciv.common.archerActionStrategies.FortifyArcherActionStrategy;
import hotciv.common.attackStrategies.AttackerAlwaysWinsAttackStrategy;
import hotciv.common.settlerActionStrategies.BuildCitySettlerActionStrategy;
import hotciv.common.winnerStrategies.DeterminedWinnerStrategy;
import hotciv.common.worldLayoutStrategies.AlphaCivWorldLayoutStrategy;
import hotciv.framework.*;

public class GammaCivFactory implements GameFactory {
	@Override
	public WinnerStrategy createWinnerStrategy() {
		return new DeterminedWinnerStrategy();
	}

	@Override
	public AgingStrategy createAgingStrategy() {
		return new LinearAgingStrategy();
	}

	@Override
	public AttackStrategy createAttackStrategy() {
		return new AttackerAlwaysWinsAttackStrategy();
	}

	@Override
	public WorldLayoutStrategy createWorldLayoutStrategy() {
		return new AlphaCivWorldLayoutStrategy();
	}

	@Override
	public ArcherActionStrategy createArcherActionStrategy() {
		return new FortifyArcherActionStrategy();
	}

	@Override
	public SettlerActionStrategy createSettlerActionStrategy() {
		return new BuildCitySettlerActionStrategy();
	}
}
