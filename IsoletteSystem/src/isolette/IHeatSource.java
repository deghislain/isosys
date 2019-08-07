package isolette;

public interface IHeatSource {

	/**
	 * This method run the heat source tasks
	 */
	public  void run(EStatus heatControl);

	/**
	 * Return the heat source current status
	 */
	public  float getHeatDelta();

}
