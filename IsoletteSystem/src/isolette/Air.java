package isolette;

public class Air implements IAir {

	/**
	 * Indicates the current heat value
	 */
	private float heat = 68;

	/**
	 * Indicates the heat's increase and decrease rate
	 */
	private float decayRate = 100.0f;

	/**
	 * Runs air tasks
	 */
	public void run(float heatDelta, EStatus thermosStatus) {
		this.updateHeat(heatDelta, thermosStatus);
	}

	/**
	 * Keeps heat up to date
	 */
	private void updateHeat(float heatDelta, EStatus thermosStatus) {
		if (thermosStatus != EStatus.OFF && thermosStatus != EStatus.FAILED) {
			if (heatDelta != 0.0f) {
				heat += heatDelta;
			} else if( thermosStatus != EStatus.INIT){
				heat -= decayRate * 0.1;
			}
		}
	}

	/**
	 * Returns Heat
	 */
	public float getHeat() {
		return heat;
	}

}
