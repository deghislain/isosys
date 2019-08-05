package isolette;

public class HeatSource implements IHeatSource {

	private EStatus HSStatus = EStatus.OFF;

	private float heatDelta = 0.0f;

	private final float heatingRate = 100.0f;

	/**
	 * This method run the heat source tasks
	 */
	public void run(EStatus heatControl) {
		this.turnHeatSourceOn(heatControl);
		this.turnHeatSourceOff(heatControl);
	}

	/**
	 * Turn the heat source on
	 */
	private void turnHeatSourceOn(EStatus heatControl) {
			if (heatControl == EStatus.ON) {
				heatDelta = heatingRate * 0.1f;
				if (heatControl == EStatus.OFF) {
					HSStatus = EStatus.ON;
				}
			}
	}

	/**
	 * Return the heat source current status
	 */
	public EStatus getHeatSourceStatus() {
		return HSStatus;
	}

	/**
	 * Turn the heat source off
	 */
	public void turnHeatSourceOff(EStatus heatControl) {
			if (heatControl == EStatus.OFF) {
				heatDelta = 0.0f;
				if (HSStatus == EStatus.ON) {
					HSStatus = EStatus.OFF;
				}
			}
	}

	public float getHeatDelta() {
		return heatDelta;
	}

}
