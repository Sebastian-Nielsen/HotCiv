package hotciv.common.GameFactories;

import hotciv.common.agingStrategies.LinearAgingStrategy;
import hotciv.common.archerActionStrategies.FortifyArcherActionStrategy;
import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
import hotciv.common.attackStrategies.AttackerAlwaysWinsAttackStrategy;
import hotciv.common.settlerActionStrategies.BuildCitySettlerActionStrategy;
import hotciv.common.settlerActionStrategies.NoSettlerActionStrategy;
import hotciv.common.winnerStrategies.AlternatingWinnerStrategy;
import hotciv.common.worldLayoutStrategies.AlphaCivWorldLayoutStrategy;
import hotciv.common.worldLayoutStrategies.CustomWorldLayoutStrategy;
import hotciv.framework.*;

import java.util.Map;

public class ThetaCivFactory implements GameFactory {
	private Map<Position, Unit> posToUnits;
	private Map<Position, City> posToCities;
	private String[] layout;

	public ThetaCivFactory(String[] layout, Map<Position, City> posToCities, Map<Position, Unit> posToUnits) {
		this.layout = layout;
		this.posToCities = posToCities;
		this.posToUnits = posToUnits;
	}

	@Override
	public WinnerStrategy createWinnerStrategy() {
		return new AlternatingWinnerStrategy();
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
		return new FortifyArcherActionStrategy();
	}

	@Override
	public SettlerActionStrategy createSettlerActionStrategy() {
		return new BuildCitySettlerActionStrategy();
	}
}
