package hotciv.common.GameFactories;

import hotciv.common.agingStrategies.LinearAgingStrategy;
import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
import hotciv.common.attackStrategies.AttackerAlwaysWinsAttackStrategy;
import hotciv.common.settlerActionStrategies.NoSettlerActionStrategy;
import hotciv.common.winnerStrategies.DeterminedWinnerStrategy;
import hotciv.common.worldLayoutStrategies.CustomWorldLayoutStrategy;
import hotciv.framework.*;

import java.util.Map;

public class DeltaCivFactory implements GameFactory {
	private Map<Position, Unit> posToUnits;
	private Map<Position, City> posToCities;
	private String[] layout;

	public DeltaCivFactory(String[] layout, Map<Position, City> posToCities, Map<Position, Unit> posToUnits) {
		this.layout = layout;
		this.posToCities = posToCities;
		this.posToUnits = posToUnits;
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
		return new CustomWorldLayoutStrategy(layout, posToCities, posToUnits);
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
