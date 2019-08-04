package isolette;

public class TemperatureSensor implements ITemperatureSensor {

	/**
	 * Indicates the current temperature
	 */
	private float currTemp;
	
	public TemperatureSensor() {
		currTemp = 68;
	}

	/**
	 * Executes temperature sensor tasks
	 */
	public void run(float heat) {
		updateCurrentTemperature(heat);
	}

	/**
	 * Keep the current temperature up to date
	 */
	private void updateCurrentTemperature(float heat) {
		currTemp = heat/100.0f;
	}

	/**
	 * Return the current temperature
	 */
	public float getCurrentTemperature() {
		return currTemp;
	}

}
