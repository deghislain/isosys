package isolette;

public class HeatSource implements IHeatSource {

	private EStatus HSStatus = EStatus.OFF;
	
	private float heatDelta = 0.0f;
	
	private final float heatingRate = 100.0f;

	/**
	 * This method run the heat source tasks
	 */
	public void run(EStatus heatControl) {
		if(heatControl == EStatus.ON && HSStatus==EStatus.OFF) {
			turnHeatSourceOn();
			heatDelta = heatingRate * 0.1f;
		}else if(heatControl == EStatus.OFF && HSStatus==EStatus.ON) {
			turnHeatSourceOff();
			heatDelta = 0.0f;
		}

	}


	/**
	 * Turn the heat source on
	 */
	private void turnHeatSourceOn() {
		HSStatus = EStatus.ON;
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
	public void turnHeatSourceOff() {
		HSStatus = EStatus.OFF;
	}
	
	public float getHeatDelta() {
		return heatDelta;
	}

}
