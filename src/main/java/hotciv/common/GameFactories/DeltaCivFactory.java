package hotciv.common.GameFactories;

import hotciv.common.agingStrategies.LinearAgingStrategy;
import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
import hotciv.common.attackStrategies.AttackerAlwaysWinsAttackStrategy;
import hotciv.common.settlerActionStrategies.NoSettlerActionStrategy;
import hotciv.common.winnerStrategies.DeterminedWinnerStrategy;
import hotciv.common.worldLayoutStrategies.DeltaCivWorldLayoutStrategy;
import hotciv.framework.*;

public class DeltaCivFactory implements GameFactory {
	private String[] layout;

	public DeltaCivFactory(String[] layout) {
		this.layout = layout;
	}

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
		return new DeltaCivWorldLayoutStrategy(layout);
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
