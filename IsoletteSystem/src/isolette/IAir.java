package isolette;

public interface IAir {

	/**
	 * Runs air tasks
	 */
	public  void run(float heatDelta);

	/**
	 * Returns Heat
	 */
	public  float getHeat();

}
