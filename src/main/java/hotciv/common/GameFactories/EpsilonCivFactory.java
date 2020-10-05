package hotciv.common.GameFactories;

import hotciv.common.*;
import hotciv.common.agingStrategies.LinearAgingStrategy;
import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
import hotciv.common.attackStrategies.CombinedStrengthAttackStrategy;
import hotciv.common.settlerActionStrategies.NoSettlerActionStrategy;
import hotciv.common.winnerStrategies.ThreeSuccessfulAttacksWinnerStrategy;
import hotciv.common.worldLayoutStrategies.AlphaCivWorldLayoutStrategy;
import hotciv.framework.*;

public class EpsilonCivFactory implements GameFactory {

	@Override
	public WinnerStrategy createWinnerStrategy() {
		return new ThreeSuccessfulAttacksWinnerStrategy();
	}

	@Override
	public AgingStrategy createAgingStrategy() {
		return new LinearAgingStrategy();
	}

	@Override
	public AttackStrategy createAttackStrategy() {
		return new CombinedStrengthAttackStrategy(new TrueRandomNumberStrategy());
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
