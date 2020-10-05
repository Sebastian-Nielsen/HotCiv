package hotciv.common.GameFactories;

import hotciv.common.attackStrategies.CombinedStrengthAttackStrategy;
import hotciv.framework.*;

public class StubEpsilonCivFactory extends EpsilonCivFactory {
	private RandomNumberStrategy randomNumberStrategy;

	public StubEpsilonCivFactory(RandomNumberStrategy randomNumberStrategy) {
		this.randomNumberStrategy = randomNumberStrategy;
	}

	@Override
	public AttackStrategy createAttackStrategy() {
		return new CombinedStrengthAttackStrategy(randomNumberStrategy);
	}

}
