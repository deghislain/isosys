package isolette;

public interface IHeatSource {

	/**
	 * This method run the heat source tasks
	 */
	public abstract void run(EStatus heatControl);

	/**
	 * Return the heat source current status
	 */
	public abstract EStatus getHeatSourceStatus();

}
