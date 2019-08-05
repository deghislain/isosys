package isolette;

public interface IAir {

	/**
	 * Runs air tasks
	 */
	public  void run(float heatDelta, EStatus thermosStatus);

	/**
	 * Returns Heat
	 */
	public  float getHeat();

}
