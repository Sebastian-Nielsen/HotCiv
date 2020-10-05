package hotciv.common.GameFactories;

import hotciv.common.agingStrategies.ProgressiveAgingStrategy;
import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
import hotciv.common.attackStrategies.AttackerAlwaysWinsAttackStrategy;
import hotciv.common.settlerActionStrategies.NoSettlerActionStrategy;
import hotciv.common.winnerStrategies.CityConquerWinnerStrategy;
import hotciv.common.worldLayoutStrategies.AlphaCivWorldLayoutStrategy;
import hotciv.framework.*;

public class BetaCivFactory implements GameFactory {
	@Override
	public WinnerStrategy createWinnerStrategy() {
		return new CityConquerWinnerStrategy();
	}

	@Override
	public AgingStrategy createAgingStrategy() {
		return new ProgressiveAgingStrategy();
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
		return new NoArcherActionStrategy();
	}

	@Override
	public SettlerActionStrategy createSettlerActionStrategy() {
		return new NoSettlerActionStrategy();
	}
}
