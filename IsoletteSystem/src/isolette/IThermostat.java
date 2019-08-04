package isolette;

public interface IThermostat {

	/**
	 * This method run thermostat tasks
	 */
	public  void run(boolean thermosCom, byte LDTempIn, byte UDTempIn, byte LATempIn, byte UATempIn, float currTemp);

	/**
	 * Return the regulator
	 */
	public IRegulator getRegulator();
	
	/**
	 * Return the regulator
	 */
	public IMonitor getMonitor();
}
