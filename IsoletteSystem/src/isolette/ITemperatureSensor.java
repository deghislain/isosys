package isolette;

public interface ITemperatureSensor {

	/**
	 * Executes temperature sensor tasks
	 */
	public  void run(float airTempIn);

	/**
	 * Return the current temperature
	 */
	public  float getCurrentTemperature();

}
