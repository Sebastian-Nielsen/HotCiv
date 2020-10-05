package hotciv.framework;

public interface GameFactory {

	public WinnerStrategy createWinnerStrategy();
	public AgingStrategy createAgingStrategy();
	public AttackStrategy createAttackStrategy();
	public WorldLayoutStrategy createWorldLayoutStrategy();
	public ArcherActionStrategy createArcherActionStrategy();
	public SettlerActionStrategy createSettlerActionStrategy();
}
