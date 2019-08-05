package isolette;

public class Air implements IAir {

	/**
	 * Indicates the current heat value
	 */
	private float heat = 6800;

	/**
	 * Indicates the heat's increase and decrease rate
	 */
	private float decayRate = 100.0f;

	/**
	 * Runs air tasks
	 */
	public void run(float heatDelta) {
		this.updateHeat(heatDelta);
	}

	/**
	 * Keeps heat up to date
	 */
	private void updateHeat(float heatDelta) {
			if (heatDelta != 0.0f) {
				heat += heatDelta;
			} else{
				heat -= decayRate * 0.1;
			}
	}

	/**
	 * Returns Heat
	 */
	public float getHeat() {
		return heat;
	}

}
