//package hotciv.common.GameFactories;
//
//import hotciv.common.agingStrategies.LinearAgingStrategy;
//import hotciv.common.archerActionStrategies.NoArcherActionStrategy;
//import hotciv.common.attackStrategies.AttackerAlwaysWinsAttackStrategy;
//import hotciv.common.settlerActionStrategies.NoSettlerActionStrategy;
//import hotciv.common.winnerStrategies.AlternatingWinnerStrategy;
//import hotciv.common.worldLayoutStrategies.AlphaCivWorldLayoutStrategy;
//import hotciv.framework.*;
//
//public class ThetaCivFactory implements GameFactory {
//	@Override
//	public WinnerStrategy createWinnerStrategy() {
//		return new AlternatingWinnerStrategy();
//	}
//
//	@Override
//	public AgingStrategy createAgingStrategy() {
//		return new LinearAgingStrategy();
//	}
//
//	@Override
//	public AttackStrategy createAttackStrategy() {
//		return new AttackerAlwaysWinsAttackStrategy();
//	}
//
//	@Override
//	public WorldLayoutStrategy createWorldLayoutStrategy() {
//		return new AlphaCivWorldLayoutStrategy();
//	}
//
//	@Override
//	public ArcherActionStrategy createArcherActionStrategy() {
//		return new NoArcherActionStrategy();
//	}
//
//	@Override
//	public SettlerActionStrategy createSettlerActionStrategy() {
//		return new NoSettlerActionStrategy();
//	}
//}
