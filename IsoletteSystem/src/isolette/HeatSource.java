package isolette;

public class HeatSource implements IHeatSource {

	private EStatus HSStatus = EStatus.OFF;

	private float heatDelta = 0.0f;

	private final float heatingRate = 100.0f;

	/**
	 * This method run the heat source tasks
	 */
	public void run(EStatus heatControl, EStatus thermosStatus) {
		this.turnHeatSourceOn(heatControl, thermosStatus);
		this.turnHeatSourceOff(heatControl, thermosStatus);
	}

	/**
	 * Turn the heat source on
	 */
	private void turnHeatSourceOn(EStatus heatControl, EStatus thermosStatus) {
		if (thermosStatus != EStatus.OFF && thermosStatus != EStatus.FAILED) {
			if (heatControl == EStatus.ON) {
				heatDelta = heatingRate * 0.1f;
				if (HSStatus == EStatus.OFF) {
					HSStatus = EStatus.ON;
				}
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
	public void turnHeatSourceOff(EStatus heatControl, EStatus thermosStatus) {
		if (thermosStatus != EStatus.OFF && thermosStatus != EStatus.FAILED) {
			if (heatControl == EStatus.OFF) {
				heatDelta = heatingRate * 0.0f;
				if (HSStatus == EStatus.ON) {
					HSStatus = EStatus.OFF;
				}
			}
		}
	}

	public float getHeatDelta() {
		return heatDelta;
	}

}
